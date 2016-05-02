//Author: John Madsen
package pkg490final.network.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import pkg490final.Packets.Packet;
import pkg490final.Packets.PacketSet;
import pkg490final.Packets.RequestLine;
import pkg490final.Packets.Response.ResponseLine;
import pkg490final.Packets.Response.ResponseMethod;

public class ClientReceiver extends RDT20Receiver {

    private PacketSet packetSet;
    private int sendingPort;
    private String serverIP;

    public ClientReceiver(String name, int receivingPort) throws SocketException {
        super(name, receivingPort);
    }

    public ClientReceiver(String name, int receivingPort, int sendingPort, String serverIP) throws SocketException {

        super(name, receivingPort);
        this.sendingPort = sendingPort;
        this.serverIP = serverIP;
        System.out.println("serverIP: " + serverIP + "\nreceiving Port: " + receivingPort + "\n sending port: " + sendingPort);
    }

    /**
     * Once an in order packet is received, it is added to the master list of
     * packets for later extraction. if its the last packet, the receiver stops
     * listening and makes a packetSet from the list to extract the data.
     *
     * @param packet: in order packet received.
     */
    public void deliverData(Packet packet) {
        packetsReceived++;

        receiverPrint("\n@@@ Receiver delivered packet #(" + packetsReceived + ") with: \n'" + packet + "'");

        packets.add(packet);
        if (packet.isLastPacket()) {
            System.out.println("Last packet received, stopped listening for packets.\nReconstructed Packet Data:\n\n");
            packetSet = PacketSet.createPacketSet(packets);
            stopListening();
        }
    }

    /**
     * starts the thread to begin listening for packets. Once a packet is
     * received the requestLine is saved and this class is passed to the current
     * state to see if it was the correct packet.
     *
     */
    @Override
    public void run() {
        try {
            receivingSocket = new DatagramSocket(receivingPort);
            while (true) {
                receiverPrint("Receiver waiting for packet");
                byte[] buf = new byte[128];
                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                receivingSocket.receive(packet);
                byte[] packetData = Arrays.copyOf(packet.getData(), packet.getLength());
                String temp = new String(packetData);
                Packet reconstructedPacket = new Packet(temp);
                try {
                    reqLine = (RequestLine) reconstructedPacket.getLine();
                } catch (Exception e) {

                }
                currentPacket = reconstructedPacket;

                state.action(this);
            }
        } catch (Exception e) {
            stopListening();
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
        System.out.println("SENDING ACK----------------\n " + serverIP + " " + sendingPort);
        DatagramPacket ackPacket = new DatagramPacket(ack.toString().getBytes(), ack.toString().length(), InetAddress.getByName(serverIP), sendingPort);

        sendingSocket.send(ackPacket);
    }

    public PacketSet getPacketSet() {
        return packetSet;
    }

}
