package pkg490final.network.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import pkg490final.P2PFile;
import pkg490final.Packets.Packet;
import pkg490final.Packets.RequestLine;
import pkg490final.Song;


/**
 * 
 * @author johndunn
 */

public class DirectoryServer {
    
    private static ArrayList<Song> masterSongList = null;
    private static ArrayList<P2PFile> masterP2PList = null;
    private static DirectoryServer instance = null;
    private DatagramSocket serverSocket = null;
    private static Map<String, Thread> threads = null;
    
    private DirectoryServer(int port) {
        try 
        { 
            serverSocket = new DatagramSocket(port);
            masterSongList = new ArrayList<>();
            threads = new HashMap<String, Thread>();
            masterP2PList = new ArrayList<>();
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Could not create server socket on port " + port +". Quitting."); 
            System.exit(-1); 
        }
    }
    
    public static DirectoryServer getInstance() {
        if(instance == null) {
            instance = new DirectoryServer(49000);
        }
        return instance;
    }

    /**
     * incomingTraffic accepts all UDP traffic from all clients. If hostname is new, a new thread is spawned, if hostname exists currently,
     * all subsequent traffic is forwarded to the corresponding thread.
     */
    public void incomingTraffic() {
        byte[] receive = new byte[128];
        try {
            while (true) {
                RDT20Receiver receiver;
                DatagramPacket inboundPacket = new DatagramPacket(receive, receive.length);
                serverSocket.receive(inboundPacket);
                
                //String packetIN = new String(inboundPacket.getData());
                System.out.println("RECEIVED PACKET ON DIRECTORY: \n");
                
                //extractData(packetData);
               // String temp = new String(packetData);
                byte[] packetData = Arrays.copyOf(inboundPacket.getData(), inboundPacket.getLength());
                String temp = new String(packetData);
                Packet reconstructedPacket = new Packet(temp);

                RequestLine reqLine = (RequestLine) reconstructedPacket.getLine();
                System.out.println(reqLine + " on directory server");
                if(runningThread(reqLine.getHostName())) {
                    receiver = (RDT20Receiver)threads.get(reqLine.getHostName());
                }
                else {
                    receiver = new RDT20Receiver(reqLine.getHostName(), reqLine.getSourcePort());
                    threads.put(reqLine.getHostName(), receiver);
                    receiver.start();
                    System.out.println("Receiver20 thread started on directory server");
                }
                receiver.incoming(reconstructedPacket);
            }
        } catch (Exception e) {
            stopListening();
        }
    }
    
    public static boolean runningThread(String user) {
        if (threads.get(user)!=null) {
                return true;
        }
        return false;
    }
        
    public void stopListening() {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
    
    public static void updateSongList(String artist, String title, int size, String IP) {
        masterSongList.add(new Song(artist, title, size, IP));       
    }
    
    public static void updateP2PList(String title, long size) {
        masterP2PList.add(new P2PFile(title, size));      
    }
    
    public static void killThread(String thread) {
        threads.get(thread).interrupt();
        threads.remove(thread);
    }
    
    //this could be converted to send song list back to client on specified port.
    public static void getSong() {
        for (Song masterSongList1 : masterSongList) {
            System.out.println(masterSongList1.toString());
        }
        System.out.println(masterSongList.size());
    }
    
}
