//Author: John Madsen
package pkg490final;

import pkg490final.Packets.Response.ResponseLine;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.URL;
import pkg490final.Packets.*;
import java.util.ArrayList;
import java.util.Arrays;
import pkg490final.Packets.Line;
import pkg490final.Packets.PacketSet;
import pkg490final.Packets.Request.RequestMethod;

public class PacketUtilities {
    
    /**
     * concatenates P2PFile file name with file size
     * @param localFiles ArrayList of P2PFile, P2PFile consists of name and size of song file.
     * @return a String of file name and size
     */
    public static String p2pFilesToString(ArrayList<P2PFile> localFiles) {
        String packetBody = "";
        for (P2PFile localFile : localFiles) { // put all headerLines into one large packetBody string
            packetBody += localFile.getName() + " " + localFile.getSize() + "\r\n";
        }
        return packetBody;
    }
    
    /**
     * 
     * @param line
     * @return 
     */
    static public Line returnLine(String line) {
        String a[] = line.split(" ");
        if (a.length == 5) {
            return new RequestLine(a);
        }
        return new ResponseLine(a);
    }

    /**
     * Extracts P2P file names from packets. Strips header information from body of the message. Body of message is added to String Array
     * @param packets
     * @return ArrayList of songs
     */
    public static ArrayList<String> extractPacket(ArrayList<String> packets) {
        String messageBody = "";

        for (String packet : packets) {
            messageBody += packet.split("\r\n\r\n")[1];
        }

        ArrayList<String> songs = new ArrayList(Arrays.asList(messageBody.split("\r\n")));

        songs.remove(songs.size() - 1);
        return songs;
    }

//    public static PacketSet extractRequestPacket(ArrayList<String> packets) {
//
//        return songs;
//    }
    /**
     * Reads IP from amazonaws
     * @return IP address
     */
    public static String getPublicIP() {
        try {
            URL url = new URL("http://checkip.amazonaws.com/");
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            return br.readLine();
        } catch (Exception e) {
            System.out.println("unable to get public IP");
        }
        return null;
    }

    /**
     * Accepts parameter of headerLines and creates ArrayList of P2PFiles. 
     * @param headerLines
     * @return ArrayList of P2PFile
     */
    static ArrayList<P2PFile> convertToP2PFile(ArrayList<String> headerLines) {
        String[] tempString;
        ArrayList<P2PFile> p2PFiles = new ArrayList();

        for (String song : headerLines) {

            P2PFile tempFile = new P2PFile();
            tempString = song.split(" ");
            tempFile.setName(tempString[0]);
            tempFile.setSize(Long.parseLong(tempString[1]));
            tempFile.convertBack();
            p2PFiles.add(tempFile);
        }
        return p2PFiles;
    }

}
