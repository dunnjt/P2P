package pkg490final.network.sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg490final.Packets.Packet;
import pkg490final.Packets.Response.ResponseMethod;

/**
 * Sender class using modified reliable data transfer 3.0 without checksums for
 * corruption. The reason for this is that the UDP protocol handles corruption.
 * This class handles sending a list of packets to another client. ACKS and
 * timeouts are used in order to ensure reliable data delivery.
 *
 *
 * @author John Madsen
 */
public class RDT30Sender {

    private int receiverPortNumber = 0; // port to receive packets from server on.
    private int sendingPortNumber = 0; // port to send packets to server on.
    private DatagramSocket sendingSocket = null;
    private DatagramSocket receivingSocket = null;
    private InetAddress internetAddress = null;
    private int packetsSent = 0; //packets that have been successfully sent and ACK'd back.
    private SenderState state; //state the Sender is currently in.
    private ArrayList<Packet> currentPackets; //current list of packets to be sent to server.
    private Timer timeOutTimer; //timer to handle timeouts when an ACK isn't received.
    private long startTime; // time when packet is initially sent, used to calculate sample RTT.
    private long sampleRTT = 1500; // time it takes to send a packet and receive ACK.
    private long estimatedRTT = 1500; //estimated time to receive ACK after sending packet. 3000 ms initial value according to rfc1122
    private double devRTT = 0; //deviation of RTT. start at 50 for buffer to give a little extra time to receive packet.

    public RDT30Sender() {

    }

    /**
     * Method to start sender. initialize with a target IP address of directory
     * server. Also set the sending port and receiving port.
     *
     * @param targetAddress local IP address of directory Server.
     * @param sendingPortNumber port to use to send data to directory server.
     * @param receiverPortNumber port to include in requestLine for directory
     * server to contact the sender back on.
     */
    public void startSender(String targetAddress, int sendingPortNumber, int receiverPortNumber) throws SocketException, UnknownHostException {

        sendingSocket = new DatagramSocket();
        receivingSocket = new DatagramSocket(receiverPortNumber);
        internetAddress = InetAddress.getByName(targetAddress);
        this.receiverPortNumber = receiverPortNumber;
        this.sendingPortNumber = sendingPortNumber;
    }

    /**
     * Close sockets to stop transmitting
     */
    public void stopSender() {

        if (sendingSocket != null) {
            sendingSocket.close();
        }
    }

    /**
     * Initialize a sending sequence in order to send a list of packets. Set
     * sender state to SenderSendState0 because 0 is the first sequence number
     * to send. Loops through the packets and sends each one by passing this
     * class into the current state. Once all packets sent this method
     * terminates.
     *
     *
     * @param packets: Packet object to be sent to the directory server.
     */
    public void initializeSend(ArrayList<Packet> packets) throws SocketException, IOException, InterruptedException {

        state = new SenderSendState0();
        packetsSent = 0;
        currentPackets = packets;

        while (packetsSent < packets.size()) {
            senderPrint("\n\nPACKET:" + (packetsSent + 1) + "-------------------------------------------------------\n\n");
            state.action(this);

        }
        senderPrint("All packets sent and received.");

    }

    /**
     * PACKET USED is called from getCurrentPacket() method.
     *
     * Used to actually send a packet to the receiver. Creates a DatagramPacket
     * from the current packet data. also sets the destination address and port
     * of the packet. Sends the packet and also sets the timeoutTimer using the
     * setTimer method.
     *
     */
    public void rdtSend() throws SocketException, IOException, InterruptedException {

        byte[] packetData = getCurrentPacket().toBytes();

        senderPrint("Sender sending packet(" + (packetsSent + 1) + "): '\n" + getCurrentPacket().toString() + "\n'");

        DatagramPacket packet = new DatagramPacket(packetData, packetData.length, internetAddress, sendingPortNumber);
        sendingSocket.send(packet);

        startTime = System.currentTimeMillis(); //used for sample RTT

        setTimer();

//         Minor pause for easier visualization only
        Thread.sleep(1200);
    }

    /**
     * This method waits for an ACK to be received and extracts the sequence
     * number from that packet to be returned.
     *
     * @return returns the ACK sequence number that was received.
     * @throws IOException
     */
    public int waitForACK() throws IOException {

        senderPrint("Sender waiting for ACK...");
        byte[] buf = new byte[128];
        // receive request
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        receivingSocket.receive(packet);

        //records sample RTT when ACK is received.
        //I needed to add this 50 ms because i was getting a timer already cancelled error if i didn't...will investigate why in the future.
        sampleRTT = System.currentTimeMillis() - startTime + 50;

        byte[] packetData = Arrays.copyOf(packet.getData(), packet.getLength());
        //reconstructs packet from DatagramPacket received.
        Packet reconstructedPacket = new Packet(new String(packetData));

        //returns the sequence number received
        if (reconstructedPacket.getResponseMethod().statusCode().equals(ResponseMethod.ACK.statusCode())) {
            return reconstructedPacket.getSeqNumber();
        } else { //if the packet received isn't an ACK response packet.
            return -1;
        }
    }


    /**
     * This class starts a timer based on Estimated and sample RTT and the
     * current deviation of the RTT.
     *
     */
    public void setTimer() {

        timeOutTimer = new Timer();

        System.out.println("Sample RTT: " + sampleRTT + "\nEstimated RTT: " + estimatedRTT);

        estimatedRTT = (long) (.875 * estimatedRTT + .125 * sampleRTT);

        devRTT = .75 * devRTT + .25 * Math.abs(sampleRTT - estimatedRTT);

        System.out.println("New Est RTT: " + estimatedRTT);
        System.out.println("devRTT: " + devRTT);
        long timeOutInterval = (long) (estimatedRTT + 4 * devRTT);
        System.out.println("TimeoutInverval: " + timeOutInterval);

        timeOutTimer.schedule(new TimeOut(), timeOutInterval);
    }

    public void incrementPacketsSent() {
        packetsSent++;
    }

    public int getPacketsSent() {
        return packetsSent;
    }    

    public Packet getCurrentPacket() {
        return currentPackets.get(packetsSent);
    }

    public void setState(SenderState state) {
        this.state = state;
    }

    public void senderPrint(String s) {
        System.out.println("###" + s);
    }

    public void cancelTimer() {
        timeOutTimer.cancel();
        timeOutTimer.purge();
    }

    /**
     * TimeOut class for scheduling the timeout interval timer.
     *
     */
    public class TimeOut extends TimerTask {

        /**
         * this method resends the packet when the timer times out.
         *
         */
        @Override
        public void run() {
            try {
                System.out.println("TIMEOUT OCCURRED");
                cancelTimer();
                rdtSend();
            } catch (Exception e) {
                System.out.println("exception occurred");
                e.printStackTrace();
            }
        }

    }
}
