package pkg490final.network.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import pkg490final.Packets.Packet;
import pkg490final.Packets.PacketSet;
import pkg490final.Packets.RequestLine;
import pkg490final.Packets.Response.ResponseLine;
import pkg490final.Packets.Response.ResponseMethod;

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

//    /**
//     * Once an in order packet is received, it is added to the master list of
//     * packets for later extraction. if its the last packet, the receiver stops
//     * listening and makes a packetSet from the list to extract the data.
//     *
//     * @param packet: in order packet received.
//     */
//    public void deliverData(Packet packet) {
//        packetsReceived++;
//
//        receiverPrint("\n@@@ Receiver delivered packet #(" + packetsReceived + ") with: \n'" + packet + "'");
//
//        packets.add(packet);
//        if (packet.isLastPacket()) {
//            System.out.println("Last packet received, stopped listening for packets.\nReconstructed Packet Data:\n\n");
//            PacketSet allPackets = new PacketSet(packets);
//            System.out.println(allPackets.data);
//        }
//    }
    /**
     * starts the thread to begin listening for packets. Once a packet is
     * received the requestLine is saved and this class is passed to the current
     * state to see if it was the correct packet.
     *
     */
//    @Override
//    public void run() {
//        try {
//            receivingSocket = new DatagramSocket(receivingPort);
//            while (true) {
//                receiverPrint("Receiver waiting for packet");
//                byte[] buf = new byte[128];
//                // receive request
//                DatagramPacket packet = new DatagramPacket(buf, buf.length);
//                receivingSocket.receive(packet);
//                byte[] packetData = Arrays.copyOf(packet.getData(), packet.getLength());
//                String temp = new String(packetData);
//                Packet reconstructedPacket = new Packet(temp);
//                reqLine = (RequestLine) reconstructedPacket.getLine();
//                currentPacket = reconstructedPacket;
//
//                state.action(this);
//            }
//        } catch (Exception e) {
//            stopListening();
//        }
//    }
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
            PacketSet allPackets = PacketSet.createPacketSet(packets);

            DirectoryServer server = DirectoryServer.getInstance();

            server.writeToMaster(allPackets.convertToP2PFiles());
            //server.deleteFromMaster(this.getName());
            //server.findPeer(null);

            server.killThread(this.getName());
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
