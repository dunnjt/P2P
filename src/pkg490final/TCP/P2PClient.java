/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.TCP;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import pkg490final.P2PFile;

/**
 * P2PClient is the Peer on P2P network that is receiving the file. After UDP
 * communication with P2PHst, P2PClient accepts socket connection from host, and
 * receives message as a byte array. Byte array is written to File Output
 * Stream.
 *
 * Must be run first
 *
 *
 * @author johndunn
 */
public class P2PClient extends Thread {

    private int serverPort;
    private ServerSocket socket;
    private ArrayList<P2PFile> fileNames;
    private String fileName;
    private static String location;

    /**
     * For multiple file download -- not currently used
     *
     * @param name
     * @param serverPort
     * @param fileNames
     */
    public P2PClient(String name, int serverPort, ArrayList<P2PFile> fileNames) {
        super(name);
        this.serverPort = serverPort;
        this.fileNames = fileNames;
    }

    /**
     * Current constructor being used
     *
     * @param name
     * @param serverPort
     * @param fileName
     */
    public P2PClient(String name, int serverPort, String fileName) {
        super(name);
        System.out.println("TCP: serverPort: " + serverPort + " fileName:  " + fileName);

        this.serverPort = serverPort;
        this.fileName = fileName;
    }

    /**
     * Start the thread to connect and begin sending
     */
    @Override
    public void run() {
        try {
            socket = new ServerSocket(serverPort);
            System.out.println("Starting TCP socket: " + serverPort);
        } catch (Exception e) {
            System.out.println("EXCEPTION OCCURED IN OPENING TCP RECEIVE SERVER SOCKET");
        }

        try {
            while (true) {

                Socket connectionSocket = socket.accept();
                DataInputStream inFromHost = new DataInputStream(connectionSocket.getInputStream());
                //DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                int length = inFromHost.readInt();
                byte[] message = new byte[length];

                if (length > 0) {

                    inFromHost.readFully(message, 0, message.length); // read the message
                }

                //output stream will have to be customized to our specific file paths.
                //FileOutputStream fos = new FileOutputStream("/Users/johndunn/Documents/CS490/P2P/files/" + fileNames.get(i).getName());
                FileOutputStream fos = new FileOutputStream(location + fileName);
                try {
                    fos.write(message);
                } catch (IOException e) {

                } finally {
                    fos.close();
                }

                //for testing
                String s = new String(message);
                System.out.println("Text Decryted : " + s);
            }

        } catch (Exception e) {

        }
    }

    public static void setFileLocation(String fileLocation) {
        location = fileLocation;
    }

    public static void main (String[] args) {
        P2PClient client = new P2PClient("test", 7014, "test1.txt");
        client.setFileLocation("/Users/johndunn/Desktop/TCPTest/");
        client.run();
        }
}
