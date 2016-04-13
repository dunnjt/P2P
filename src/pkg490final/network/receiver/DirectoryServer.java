package pkg490final.network.receiver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import pkg490final.Song;

public class DirectoryServer {
    
    private static ArrayList<Song> masterSongList = null;
    private static DirectoryServer instance = null;
    private DatagramSocket serverSocket = null;
    
    private DirectoryServer(int port) {
        try 
        { 
            serverSocket = new DatagramSocket(port);
            masterSongList = new ArrayList<>();
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Could not create server socket on port " + port +". Quitting."); 
            System.exit(-1); 
        }
    }
    
    public static DirectoryServer getInstance() {
        if(instance == null) {
            instance = new DirectoryServer(9876);
        }
        return instance;
    }

    
    public void incomingTraffic() {
        
        byte[] receive = new byte[13];
        try {
            while (true) {
                DatagramPacket inboundPacket = new DatagramPacket(receive, receive.length);
                serverSocket.receive(inboundPacket);
                String packetData = new String(inboundPacket.getData());
                System.out.println("RECEIVED ON DIRECTORY: \n" + packetData);
                extractData(packetData);
            }
        } catch (Exception e) {
            stopListening();
        }
    }
    
    public void extractData(String packetData) throws Exception {
       String requestLine = packetData;
        Random rand = new Random();
        
        int port = rand.nextInt((9000-6000)+1)+6000;
        
        new RDT20Receiver(requestLine, port).start();

        updateClient(port, InetAddress.getByName(requestLine), Integer.toString(port));

    }

    public void updateClient(int port, InetAddress address, String data) throws SocketException, IOException, InterruptedException {
        
        byte[] packetReturn = new byte[4];
        packetReturn = data.getBytes();
                
        DatagramPacket returnPacket = new DatagramPacket(packetReturn, packetReturn.length, address, 9876);
        serverSocket.send(returnPacket);

    }
        
    public void stopListening() {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }
    
    public static void updateSongList(String artist, String title, int size, String IP) {
        masterSongList.add(new Song(artist, title, size, IP));       
    }
    
    //this could be converted to send song list back to client on specified port.
    public static void getSong() {
        for (Song masterSongList1 : masterSongList) {
            System.out.println(masterSongList1.toString());
        }
        System.out.println(masterSongList.size());
    }
    
}
