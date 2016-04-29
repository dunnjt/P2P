//Author: John Madsen
package pkg490final.network.receiver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import pkg490final.Packets.Packet;
import pkg490final.Packets.PacketSet;
import pkg490final.Packets.RequestLine;

public class ClientReceiver extends RDT20Receiver {

    public ClientReceiver(String name, int receivingPort) throws SocketException {
        super(name, receivingPort);
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
            PacketSet allPackets = PacketSet.createPacketSet(packets);
            System.out.println(allPackets.getData());
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
                reqLine = (RequestLine) reconstructedPacket.getLine();
                currentPacket = reconstructedPacket;

                state.action(this);
            }
        } catch (Exception e) {
            stopListening();
        }
    }

}
