/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.TCP;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * P2PHost is the Peer on P2P network that is holding the file. After UDP communication from P2P client, P2PHost establishes TCP socket connection,
 * for which P2PClient will accept. Host transfers file data as byte array.
 * @author johndunn
 */
public class P2PHost extends Thread {

    private int port;
    private Socket socketIn;

    public P2PHost(String name, int port) {
        super(name);
        this.port = port;
        try {
            socketIn = new Socket("localhost", port);
        } catch (Exception e) {
        }
    }

    /**
     * Start the thread to begin listening
     */
    public void run() {

        try {
            Path path = Paths.get("/Users/johndunn/Desktop/client1Files/imageC1.jpg");
            byte[] data = Files.readAllBytes(path);

            //socketIn = new Socket("localhost", port);
            DataOutputStream outToServer = new DataOutputStream(socketIn.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));

            outToServer.writeInt(data.length);
            outToServer.write(data);

            
        } catch (Exception e) {

        } finally {
            try {
            socketIn.close();
            } catch (Exception e) {
                
            }
        }
    }

}
