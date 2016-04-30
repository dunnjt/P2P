//Author: John Madsen
package pkg490final.network;

import pkg490final.IOFunctions;
import pkg490final.Packets.PacketSet;
import pkg490final.Packets.Request.INFRequestPacketSet;
import pkg490final.Packets.Response.LISTResponsePacketSet;
import pkg490final.network.sender.RDT30Sender;

/**
 * Creates a client to send files to the directory server. Start Directory
 * server before Client
 *
 * @author John Madsen
 */
public class ClientTest {

    public static void main(String[] args) {

        //ENTER PUBLIC IP OF computer running DirectoryServer below:
        start(pkg490final.PacketUtilities.getPublicIP());
    }

    public static void start(String target) {
        try {

            target = "127.0.0.1";
            //if using public address, must forward port 33000 to local ip on your router to receive acks from server.
            RDT30Sender sender = new RDT30Sender();
            sender.startSender(target, 33000, 2014);

            //read local files from a directory and create a inform and update packet set.
            PacketSet p = new LISTResponsePacketSet(IOFunctions.readLocalFiles());

            // Send the data
            sender.initializeSend(p.getPackets());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
