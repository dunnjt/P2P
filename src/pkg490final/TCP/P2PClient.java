/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.*;
import java.net.*;

/**
 * P2PClient is the Peer on P2P network that is receiving the file. After UDP communication with P2PHst, P2PClient accepts socket connection from host,
 * and receives message as a byte array. Byte array is written to File Output Stream. 
 * @author johndunn
 */
public class P2PClient extends Thread {

    private int serverPort;
    private ServerSocket socket;

    public P2PClient(String name, int serverPort) {
        super(name);
        this.serverPort = serverPort;
    }

    /**
     * Start the thread to connect and begin sending
     */
    @Override
    public void run() {
        try {
            socket = new ServerSocket(serverPort);
        } catch (Exception e) {

        }

        try {
            while (true) {
                Socket connectionSocket = socket.accept();
                DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

                int length = inFromClient.readInt();
                byte[] message = new byte[length];

                if (length > 0) {

                    inFromClient.readFully(message, 0, message.length); // read the message
                }

                //output stream will have to be customized to our specific file paths.
                FileOutputStream fos = new FileOutputStream("/Users/johndunn/Documents/CS490/P2P/files");
                try {
                    fos.write(message);
                } catch (IOException e) {

                } finally {
                    fos.close();
                }

                //for testing
                //String s = new String(message);
                //System.out.println("Text Decryted : " + s);
            }
        } catch (Exception e) {

        }
    }
}
