/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.TCP;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg490final.P2PFile;
import pkg490final.network.sender.ClientMainPanel;

/**
 * P2PHost is the Peer on P2P network that is holding the file. After UDP
 * communication from P2P client, P2PHost establishes TCP socket connection, for
 * which P2PClient will accept. Host transfers file data as byte array.
 *
 * @author johndunn
 */
public class P2PHost extends Thread {

    private Socket socket;
    private ArrayList<P2PFile> fileNames;
    private String fileName;
    private static String location;

    /**
     * P2PHost constructor converts string IP address pulled from UDP packet requesting the file, 
     * and converts to Inet Address to create socket connection to that peer.
     * @param name name of thread
     * @param port TCP port, 7014 used for the purposes of this project
     * @param localAddr String of P2PClient
     * @param fileName name of file that CLient is requesting.
     */
    public P2PHost(String name, int port, String localAddr, String fileName) {
        super(name);
        this.fileName = fileName;
        try {
            System.out.println("Host started");
            InetAddress address = InetAddress.getByName(localAddr);
            socket = new Socket(address, port);
            System.out.println("created TCP socket on Host: port " + port + " Addr " + localAddr);

        } catch (Exception e) {
            Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, e);

            System.out.println("Error creating TCP socket on Host: port " + port + " Addr " + localAddr);
        }
    }

    /**
     * Not using this constructor at the moment.
     * @param name
     * @param port
     * @param fileName 
     */
    public P2PHost(String name, int port, String fileName) {
        super(name);
        this.fileName = fileName;
        try {
            socket = new Socket("192.168.1.2", port);
        } catch (Exception e) {
            Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, e);

            System.out.println("Error");
        }
    }

    /**
     * run() it the main sending method in the thread. It writes byte array of specified file path to the output stream.
     * Exceptions are caught, and finally, socket is closed in try, catch, finally clause.
     */
    public void run() {

        try {
            fileName = fileName.replace("|", " ");
            Path path = Paths.get(location + fileName);
            System.out.println("Path of file requested " + path);
            System.out.println(path);
            byte[] data = Files.readAllBytes(path);

            //socket = new Socket("localhost", port);
            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
            //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));

            outToClient.writeInt(data.length);
            outToClient.write(data);

        } catch (Exception e) {
            System.out.println("Exception caught on P2PHost run");

        } finally {
            try {
                socket.close();
            } catch (Exception e) {

            }
        }
    }

    /**
     * setFileLocation determines to where the downloads will be written.
     * @param fileLocation path of file location as String.
     */
    public static void setFileLocation(String fileLocation) {
        location = fileLocation;
    }

    public static void main(String[] args) {
        P2PHost host = new P2PHost("test", 7014, "test1.txt");
        host.setFileLocation("/Users/johndunn/Desktop/p2p/");
        host.run();
    }
}
