/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.TCP;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pkg490final.P2PFile;
import pkg490final.network.sender.ClientMainPanel;

/**
 * P2PClient is the Peer on P2P network that is receiving the file. After UDP
 * communication with P2PHost, P2PClient accepts socket connection from host, and
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
    private static boolean isDownloaded;

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
     * Current constructor being used P2PClient. This object is created when a peer chooses to download a file.
     *
     * @param name is the name of thread.
     * @param serverPort is the TCP port, designated as 7014 for the purpose of this project.
     * @param fileName the name of the file that the client will write to the output stream.
     */
    public P2PClient(String name, int serverPort, String fileName) {
        super(name);
        this.serverPort = serverPort;
        this.fileName = fileName.replace("|", " ");
        isDownloaded = false;
        System.out.println("TCP: serverPort: " + serverPort + " fileName:  " + this.fileName);

    }

    /**
     * startListening() responsible for create a new socket object and catching exception if bind error.
     */
    public void startListening() {
        try {
            socket = new ServerSocket(serverPort);
            System.out.println("Starting TCP socket: " + serverPort);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("EXCEPTION OCCURED IN OPENING TCP RECEIVE SERVER SOCKET");
        }
    }

    /**
     * run() it the main receiving method in the thread. It accepts socket connection from host sending file and reads byte array message from host.
     * File is written in output stream as byte array. Exceptions are caught, and finally socket is closed in try, catch, finally clause.
     */
    @Override
    public void run() {

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
                    System.out.println("Error Writing the message to a file");
                    Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, e);

                } finally {
                    fos.close();
                }

                //for testing
                System.out.println("Download " + fileName + " successfully");
                setFinished(true);
            }

        } catch (IOException e) {
            System.out.println("Likely File or Path naming error");
            Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, e);

        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing Socket");
                Logger.getLogger(ClientMainPanel.class.getName()).log(Level.SEVERE, null, e);

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

    /**
     * setFinished boolean is used in combination with isFinished() to assist in starting and stopping threads after download.
     * @param downloaded 
     */
    public static void setFinished(boolean downloaded) {
        isDownloaded = downloaded;
    }

    /**
     * isFinished() is called from ClientMainPanel to determine if the thread has executed fully, by determining if the file has been downloaded fully.
     * @return 
     */
    public boolean isFinished() {
        return isDownloaded;
    }

    /**
     * setFileName is a method for file name visualization purposes.
     * @param fileName 
     */
    public void setFileName(String fileName) {
        this.fileName = fileName.replace("|", " ");
    }

    public static void main(String[] args) {
        P2PClient client = new P2PClient("test", 7014, "test1.txt");
        client.setFileLocation("/Users/johndunn/Desktop/TCPTest/");
        client.run();
    }
}
