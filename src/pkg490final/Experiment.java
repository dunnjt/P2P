/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Arrays;

import java.io.*;
import java.net.*;

/**
 *
 * @author john
 */
public class Experiment {

    public static void main(String[] args) throws Exception {

        String requestLine = "INF JohnM-PC 24.161.23.112\r\n\r\n"; // sample request line
        ArrayList<String> headerLines = new ArrayList();
        headerLines.add("Good_Times_Bad_Times LedZeppelin 7235234\r\n");
        headerLines.add("Communication_Breakdown LedZeppelin 7235234\r\n");
        headerLines.add("Dazed_and_Confuse LedZeppelin 7235234\r\n");
        headerLines.add("Babe_I'm_Gonna_Leave_You LedZeppelin 7235234\r\n");
        headerLines.add("Whole_Lotta_Love LedZeppelin 7235234\r\n");
        headerLines.add("Ramble_On LedZeppelin 7235234\r\n");
        headerLines.add("Heartbreaker LedZeppelin 7235234\r\n");
        headerLines.add("Immigrant_Song LedZeppelin 7235234\r\n");
        headerLines.add("Since_I've_Been_Loving_You LedZeppelin 7235234\r\n");
        headerLines.add("Rock_and_Roll LedZeppelin 7235234\r\n");
        headerLines.add("Black_Dog LedZeppelin 7235234\r\n");
        headerLines.add("When_the_Levee_Breaks LedZeppelin 7235234\r\n");
        headerLines.add("Stairway_to_Heaven LedZeppelin 7235234\r\n");

        headerLines = createPackets(requestLine, headerLines);
//        for (String headerLine : headerLines) {
//            System.out.println(headerLine + "\n---\n");
//        }
//        extractData(headerLines);
        send(headerLines);
    }

    static ArrayList<String> createPackets(String requestLine, ArrayList<String> headerLines) {
        String packetBody = "";
        ArrayList<String> packets = new ArrayList();
        int counter = 0;
        int bodyLength = 118 - requestLine.length(); // 10 bytes left for ACK and seq numbers

        for (String header : headerLines) { // put all headerLines into one large packetBody string
            packetBody += header;
        }
//        packetBody += "\r\n";

        while (counter < packetBody.length()) {

            if (counter + bodyLength > packetBody.length()) {
                packets.add(requestLine + packetBody.substring(counter));
            } else {
                packets.add(requestLine + packetBody.substring(counter, counter + bodyLength));
            }
            counter += bodyLength;
        }
        return packets;
    }

    static void rdt_Send(ArrayList<String> packets) {
        Random rand = new Random();
        int seqNumber = rand.nextInt(9999);
        String tempPacket;
        HashMap<Integer, String> packetMap = new HashMap<Integer, String>();

        for (String packet : packets) {
            tempPacket = packet;
            packetMap.put(seqNumber, packet);
        }

    }

    public static void send(ArrayList<String> packets) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IP = InetAddress.getByName("localhost");
        DatagramPacket sendPacket;
        byte[] data;

        for (int i = 0; i < packets.size(); i++) {
            data = packets.get(i).getBytes();
            sendPacket = new DatagramPacket(data, data.length, IP, 8484);
            clientSocket.send(sendPacket);
        }

        clientSocket.close();
    }

    static void extractData(ArrayList<String> Packets) {
        String messageBody = "";
        for (String packet : Packets) {
            messageBody += packet.split("\r\n\r\n")[1];
        }

        String requestLine = "";
        for (String packet : Packets) {
            requestLine += packet.split("\r\n\r\n")[0];
        }
        // System.out.println(requestLine);
        ArrayList<String> requestArray = new ArrayList<>();
        for (String requestSplit : requestLine.split(" ", 0)) {
            requestArray.add(requestSplit);
        }

        System.out.println(requestArray.get(0));
        System.out.println(requestArray.get(1));
        System.out.println(requestArray.get(requestArray.size() - 1));
        
        ArrayList <String> songHelper = new ArrayList<>();
        for (String songsToArray: messageBody.split(" ", 0)) {
            songHelper.add(songsToArray);
        }
        for (int i=0; i<songHelper.size(); i++) {
            System.out.println(songHelper.get(i));
        }

    }

}
