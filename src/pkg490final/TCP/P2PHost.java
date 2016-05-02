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
import pkg490final.P2PFile;

/**
 * P2PHost is the Peer on P2P network that is holding the file. After UDP communication from P2P client, P2PHost establishes TCP socket connection,
 * for which P2PClient will accept. Host transfers file data as byte array.
 * 
 *
 * 
 * @author johndunn
 */
public class P2PHost extends Thread {

    private Socket socket;
    private ArrayList<P2PFile> fileNames;
    private String fileName;
    private static String location;

    public P2PHost(String name, int port, InetAddress localAddr, int localPort, String fileName) {
        super(name);
        this.fileName = fileName;
        try {
            socket = new Socket("localhost", port, localAddr, localPort);
        } catch (Exception e) {
        }
    }
    
    public P2PHost(String name, int port, String fileName) {
        super(name);
        this.fileName = fileName;
        try {
            socket = new Socket("localhost", port);
        } catch (Exception e) {
        }
    }

    /**
     * Start the thread to begin listening
     */
    public void run() {

        try {

            Path path = Paths.get(location + fileName);
                System.out.println(path);
                byte[] data = Files.readAllBytes(path);

                //socket = new Socket("localhost", port);
                DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
                //BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));

                outToClient.writeInt(data.length);
                outToClient.write(data);

            
        } catch (Exception e) {

        } finally {
            try {
                socket.close();
            } catch (Exception e) {
                
            }
        }
    }
    
    public static void setFileLocation (String fileLocation) {
        location = fileLocation;
    }
    
//    public static void main (String[] args) {
//        P2PHost host = new P2PHost("test", 33000, "test1.txt");
//        host.run();
//    }

}
