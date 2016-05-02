/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.network.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import pkg490final.P2PFile;
import pkg490final.Packets.Packet;
import pkg490final.Packets.PacketSet;
import pkg490final.Packets.Request.RequestPacketSet;
import pkg490final.Packets.RequestLine;
import pkg490final.Packets.Response.ResponseLine;
import pkg490final.Packets.Response.ResponseMethod;
import pkg490final.TCP.P2PHost;

/**
 *
 * @author johndunn
 */

public class ClientReceiverDown extends ClientReceiver {

    private PacketSet packetSet;
    private int sendingPort;
    private String serverIP;

    /**
     * constructor for Receiver to initialize receiving Port and thread name.
     * Also initialized state to receiverState0 to wait for seq 0 on a packet.
     *
     *
     * @param name: name of server thread.
     * @param receivingPort: port to receive traffic on.
     */
    public ClientReceiverDown(String name, int receivingPort) throws SocketException {
        super(name, receivingPort);
        sendingSocket = new DatagramSocket();

        this.receivingPort = receivingPort;
        state = new ReceiverState0();
        packetsReceived = 0;
    }
    
    public ClientReceiverDown(String name, int receivingPort, int sendingPort, String serverIP) throws SocketException {

        super(name, receivingPort);
        this.sendingPort = sendingPort;
        this.serverIP = serverIP;
        System.out.println("serverIP: " + serverIP + "\nreceiving Port: " + receivingPort + "\n sending port: " + sendingPort);
    }

    @Override
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

   public void responseProcedure(PacketSet packets) {
       ArrayList<P2PFile> files = packets.convertToP2PFiles();
       
       //port 7014 temp port assignment for all host side connections
        P2PHost host = new P2PHost(this.getName(), 3014, files.get(0).getIp(), 3014, files.get(0).getName());
        host.start();          
   }
   
    //for testing purposes
    public void responseProcedure(ArrayList<P2PFile> packets) {
        ArrayList<P2PFile> files = packets;
       
        P2PHost host = new P2PHost(this.getName(), 3014, packets.get(0).getIp(), 3014, files.get(0).getName());
        host.start();          
    }
    
    
        /**
     * Send ACK response packet to the client to respond that the last packet
     * has been received. The ACK number depends on what the receiver was
     * expecting and what it actually received.
     *
     * @param seqNumber the sequence number of the ACK to be sent.
     */
    @Override
    public void sendACK(int seqNumber) throws UnknownHostException, IOException {

        receiverPrint("sending ACK: " + seqNumber);
        Packet ack = new Packet(new ResponseLine(ResponseMethod.ACK), " ", true);
        ack.setSeqNumber(seqNumber);

        //sends ACK back to ip and port specified in request line.
        DatagramPacket ackPacket = new DatagramPacket(ack.toString().getBytes(), ack.toString().length(), reqLine.getIp(), reqLine.getSourcePort());

        sendingSocket.send(ackPacket);
    }
}