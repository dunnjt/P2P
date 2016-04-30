package pkg490final.network.receiver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;
import pkg490final.P2PFile;
import pkg490final.Packets.Packet;
import pkg490final.Packets.RequestLine;
import pkg490final.Song;

/**
 * Singleton Directory Server. Once server is started single instance of class
 * is instantiated. This instance is responsible for all UDP traffic and routes
 * that traffic to either a new RDT thread or currently running RDT thread.
 * Class holds single instance to masterP2PList which is an ArrayList of all P2P
 * files that are currently available by connected peers.
 *
 * @author johndunn
 */
public class DirectoryServer {

    private static ArrayList<Song> masterSongList = null;
    private static ArrayList<P2PFile> masterP2PList = null;
    private static DirectoryServer instance = null;
    private DatagramSocket serverSocket = null;
    private static Map<String, Thread> threads = null;
    private static ArrayList<String> threadNames = null;

    /**
     * Directory Server has private constructor that can only be called from
     * within class. Ensures only one instance instantiated.
     *
     * @param port determines which port all UDP traffic will be filtered
     * through.
     */
    private DirectoryServer(int port) {
        try {
            System.out.println("ON PORT: " + port);
            serverSocket = new DatagramSocket(port);
            masterSongList = new ArrayList<>();
            threads = new HashMap<>();
            masterP2PList = new ArrayList<>();
            threadNames = new ArrayList<>();
        } catch (IOException ioe) {
            System.out.println("Could not create server socket on port " + port + ". Quitting.");
            System.exit(-1);
        }
    }

    /**
     * getInstance() returns DirectoryServer instance if one exists or
     * instantiates if null.
     *
     * @return DirectoryServer instance.
     */
    public static DirectoryServer getInstance() {
        if (instance == null) {
            instance = new DirectoryServer(4014);
        }
        return instance;
    }

    /**
     * run() accepts all UDP traffic from all clients. If hostname is new, a new
     * thread is spawned, if hostname exists currently, all subsequent traffic
     * is forwarded to the corresponding thread.
     */
    public void run() {
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
                if (runningThread(reqLine.getHostName())) {
                    receiver = (RDT20Receiver) threads.get(reqLine.getHostName());
                } else {
                    receiver = new RDT20Receiver(reqLine.getHostName(), reqLine.getSourcePort());
                    threads.put(reqLine.getHostName(), receiver);
                    threadNames.add(reqLine.getHostName());
                    receiver.start();
                    System.out.println("Receiver20 thread started on directory server");
                }
                receiver.incoming(reconstructedPacket);
            }
        } catch (Exception e) {
            System.out.println("CAUGHT EXCEPTION ON DIRECTORY SERVER");
            stopListening();
        }
    }

    /**
     * runningThread() checks if thread for client currently exists.
     *
     * @param user takes parameter of hostname to keep track of running threads
     * @return boolean condition if thread exists.
     */
    public static boolean runningThread(String user) {
        if (threads.get(user) != null) {
            return true;
        }
        return false;
    }

    /**
     * stopListening() shuts down server if exception is caught.
     */
    public void stopListening() {
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

    /**
     * writeToMaster concats masterP2pFile with any new client P2PFile list.
     *
     * @param list takes parameter of new client P2PFile list.
     */
    public synchronized void writeToMaster(ArrayList<P2PFile> list) {
        masterP2PList.addAll(list);
    }

    /**
     * deleteFromMaster() iterates through masterP2PList and removes those that
     * match disconnecting client.
     *
     * @param client that is removing themselves from peer network.
     */
    public synchronized void deleteFromMaster(String client) {
        for (int i = 0; i < masterP2PList.size(); i++) {
            if (masterP2PList.get(i).getHostName().equals(client)) {
                masterP2PList.remove(masterP2PList.get(i));
            }
        }
    }

    /**
     * findPeer() searches through masterP2PList for peer holding specified
     * filename.
     *
     * @param fileName the filename one peer is requesting from another
     * @return IP address as String of P2P client that with act as Server for
     * TCP connection.
     */
    public synchronized String findPeer(String fileName) {
        for (int i = 0; i < masterP2PList.size(); i++) {
            if (masterP2PList.get(i).getName().equals(fileName)) {
                return masterP2PList.get(i).getIp();
            }
        }
        return null;
    }

    public synchronized ArrayList<P2PFile> getMasterList() {
        return masterP2PList;
    }

    public synchronized ArrayList<P2PFile> qryMasterList(String qry) {
        if (qry.equals("\u0004")) {
            return masterP2PList;
        }
        ArrayList<P2PFile> returnList = new ArrayList<>();
        for (P2PFile p2pFile : masterP2PList) {
            if (p2pFile.getName().toLowerCase().contains(qry.toLowerCase())) {
                returnList.add(p2pFile);
            }
        }
        return returnList;

    }

    public synchronized void killThread(String thread) {
        threads.get(thread).interrupt();
        threadNames.remove(thread);
        threads.remove(thread);
    }

    public synchronized void restartReceiver(String thread, int receivingPort) throws SocketException {
        threads.put(thread, new RDT20Receiver(thread, receivingPort));
    }

    public int getIndexOfKey(String hostName) {
        return threadNames.indexOf(hostName);
    }

    public void print() {
        for (int i = 0; i < masterP2PList.size(); i++) {
            System.out.println(masterP2PList.get(i).toString());

        }
    }

}
