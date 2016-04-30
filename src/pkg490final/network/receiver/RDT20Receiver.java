package pkg490final.network.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg490final.Packets.Packet;
import pkg490final.Packets.PacketSet;
import pkg490final.Packets.Request.RequestMethod;
import pkg490final.Packets.Request.RequestPacketSet;
import pkg490final.Packets.RequestLine;
import pkg490final.Packets.Response.ERRORResponsePacketSet;
import pkg490final.Packets.Response.LISTResponsePacketSet;
import pkg490final.Packets.Response.OKResponsePacketSet;
import pkg490final.Packets.Response.ResponseLine;
import pkg490final.Packets.Response.ResponseMethod;
import pkg490final.Packets.Response.ResponsePacketSet;
import pkg490final.network.sender.RDT30Sender;

/**
 * RDT20 modified to have no checksums because UDP handles corruption. This
 * class receives packets and puts them in a list. When a packet is received and
 * the sequence number is correct it is delivered. an ACK is sent after a packet
 * is receiver with the sequence number matching the last in order packet
 * received.
 *
 * @author John Madsen
 */
public class RDT20Receiver extends Thread {

    protected int receivingPort; // port used to receive packets from the client on.
    protected DatagramSocket receivingSocket = null; //socket used to receive packets from client on.
    protected ArrayList<Packet> packets = new ArrayList(); //list of packets received in correct order.
    protected ReceiverState state; //state that receiver is currently in (waiting for packet 1 or 0)
    protected DatagramSocket sendingSocket = null; //socket to send ACKs to client through.
    protected Packet currentPacket; //last packet received.
    protected int packetsReceived; //number of packets received.
    protected RequestLine reqLine; //request line of last packet received to extract IP and port from.

    /**
     * constructor for Receiver to initialize receiving Port and thread name.
     * Also initialized state to receiverState0 to wait for seq 0 on a packet.
     *
     *
     * @param name: name of server thread.
     * @param receivingPort: port to receive traffic on.
     */
    public RDT20Receiver(String name, int receivingPort) throws SocketException {
        super(name);
        sendingSocket = new DatagramSocket();

        this.receivingPort = receivingPort;
        state = new ReceiverState0();
        packetsReceived = 0;
    }

    /**
     * To close socket and stop listening for packets.
     *
     */
    public void stopListening() {
        if (receivingSocket != null) {
            receivingSocket.close();
        }
    }

    public void run() {
        System.out.println("Receiver " + this.getName() + " waiting for packet");
    }

    public void incoming(Packet packetData) throws Exception {
        reqLine = (RequestLine) packetData.getLine();
        currentPacket = packetData;
        state.action(this);
    }

    public void deliverData(Packet packet) {
        packetsReceived++;
        packets.add(packet);
        if (packet.isLastPacket()) {
            //System.out.println("Last packet received, stopped listening for packets.\nReconstructed Packet Data:\n\n");
            stopListening();
            RequestPacketSet allPackets = (RequestPacketSet) PacketSet.createPacketSet(packets);
            responseProcedure(allPackets);
        }
    }

    public void responseProcedure(RequestPacketSet reqPS) {
        DirectoryServer server = DirectoryServer.getInstance();
        RDT30Sender sender = new RDT30Sender();
        ResponsePacketSet ResPS = new ERRORResponsePacketSet();
        int ackPort = server.getIndexOfKey(this.getName()) * 1000 + 5014;
        System.out.println("port used to accept ACKS on is: " + ackPort);
        try {
            sender.startSender(reqPS.getRequestLine().getIpString(), reqPS.getRequestLine().getSourcePort(), ackPort);
        } catch (SocketException ex) {
            Logger.getLogger(RDT20Receiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(RDT20Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (reqPS.getRequestMethod() == RequestMethod.INF) {
            server.writeToMaster(reqPS.convertToP2PFiles());
            ResPS = new OKResponsePacketSet();
        } else if (reqPS.getRequestMethod() == RequestMethod.QRY) {
            ResPS = new LISTResponsePacketSet(server.qryMasterList(reqPS.getPacketBody()));

        }
        try {
            sender.initializeSend(ResPS.getPackets());
        } catch (IOException ex) {
            Logger.getLogger(RDT20Receiver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(RDT20Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }
        sender.stopSender();
        try {
            server.restartReceiver(this.getName(), receivingPort);
        } catch (SocketException ex) {
            Logger.getLogger(RDT20Receiver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Send ACK response packet to the client to respond that the last packet
     * has been received. The ACK number depends on what the receiver was
     * expecting and what it actually received.
     *
     * @param seqNumber the sequence number of the ACK to be sent.
     */
    public void sendACK(int seqNumber) throws UnknownHostException, IOException {

        receiverPrint("sending ACK: " + seqNumber);
        Packet ack = new Packet(new ResponseLine(ResponseMethod.ACK), " ", true);
        ack.setSeqNumber(seqNumber);

        //sends ACK back to ip and port specified in request line.
        DatagramPacket ackPacket = new DatagramPacket(ack.toString().getBytes(), ack.toString().length(), reqLine.getIp(), reqLine.getSourcePort());

        sendingSocket.send(ackPacket);
    }

    public Packet getCurrentPacket() {
        return currentPacket;
    }

    public void setState(ReceiverState state) {
        this.state = state;
    }
   

    /**
     * simple method to append @@@ in front of messages by the receiver to
     * differentiate it from a sender if both are running on the same PC.
     *
     * @param s: string to append and print.
     */
    public void receiverPrint(String s) {
        System.out.println("@@@" + s);
    }

}
