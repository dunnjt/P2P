/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.network;


import pkg490final.network.sender.RDT30Sender;
import pkg490final.network.receiver.RDT20Receiver;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import pkg490final.IOFunctions;
import pkg490final.Packets.PacketSet;
import pkg490final.Packets.Request.INFRequestPacketSet;

/**
 * These classes contain a very simple example of a UDT send and receive similar
 * to the RDT 1.0 covered in class. A receiver thread is started that begins
 * listening on 49000 that waits for packets to delivers. A sending socket is
 * then opened for sending data to the receiver.
 *
 * With a real sender a large message will need to be broken into packets so
 * this demo also shows one way to break a larger message up this way.
 *
 * @author Chad Williams
 */
public class Main2 {

    public static void main(String[] args) {
        RDT20Receiver receiverThread = null;
        try {
//             Start receiver -- comment this out on sender client
            receiverThread = new RDT20Receiver("Receiver", 49000);
            receiverThread.start();

            // Create sender --comment the rest of this out on receiver.
//            JOHN M public address. ports forwarded to desktop.
            byte[] targetAdddress = {(byte) 127, (byte) 0, 0, (byte) 1};
//            //JOHN DUNN public address.
//            byte[] targetAdddress = {(byte) 73, (byte) 159, 0, (byte) 71};

//            RDT30Sender sender = new RDT30Sender();
//            sender.startSender(targetAdddress, 49000, 33000);
//            PacketSet p = new INFRequestPacketSet(IOFunctions.readLocalFiles());
//
////            for (int i = 0; i < 10; i++) {
//            // Send the data
//            sender.initializeSend(p.getPackets());
//            // Sleeping simply for demo visualization purposes
//            Thread.sleep(10000);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
