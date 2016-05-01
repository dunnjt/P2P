/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.Packets;

import java.util.ArrayList;
import pkg490final.P2PFile;

/**
 * All the information held to make up numerous packets from one long string of
 * data and the request / response lines.
 *
 * @author john
 */
public abstract class PacketSet {

    private Line line;
    private String data = "";
    private ArrayList<Packet> packets;

    /**
     * for creating packetSet on sender side;
     *
     * @param line request/response line
     * @param data data to be split up inside packets.
     */
    public PacketSet(Line line, String data) {
        this.line = line;
        this.data = data;
        createPackets();
    }

    public PacketSet() {

    }

    /**
     * for creating packetSet from set of packets on receiver side
     *
     * @param packets received
     */
    private PacketSet(ArrayList<Packet> packets) {
        line = packets.get(0).getLine();
        this.packets = packets;
        for (Packet packet : packets) {
            data += packet.getPacketBody();
        }
    }

    public static PacketSet createPacketSet(ArrayList<Packet> packets) {
        return PacketSetFactory.createPacketSet(packets);
    }

    /**
     * creates all the 128 byte packets from the data and request/response lines
     * to be ready to be sent and puts them in the packets list stored within
     * the class. Also puts a flag on the last packet so the receiver knows when
     * to stop listening.
     */
    public void createPackets() {
        packets = new ArrayList();
        int counter = 0;
        int bodyLength = 127 - getLine().size();
        int i = getPacketBody().length();
        if (getPacketBody().length() == 0) {
            packets.add(new Packet(line, "\u0004", true));
        } else {
            while (counter < getPacketBody().length()) {
                if (counter + bodyLength > getPacketBody().length()) {
                    packets.add(new Packet(line, getPacketBody().substring(counter), true));
                } else {
                    packets.add(new Packet(line, getPacketBody().substring(counter, counter + bodyLength), false));
                }
                counter += bodyLength;
            }
        }
    }

    public void setPacketBodyEnd(String packetBody) {
        this.data = packetBody + "\u0004";
    }
    public void setPacketBody(String packetBody){
        this.data = packetBody;
    }

    public String getPacketBody() {
        return data;
    }

    public String getData() {
        return data;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public ArrayList<Packet> getPackets() {
        return packets;
    }

    public Line getLine() {
        return line;
    }

    public ArrayList<P2PFile> convertToP2PFiles() {
        ArrayList<P2PFile> list = new ArrayList<>();
        String convert = data.replaceAll("[\n]", " ");
        String[] listString = convert.split(" ");
        for (int i = 0; i < listString.length - 3; i = i + 4) {
            list.add(new P2PFile(listString[i], Long.parseLong(listString[i + 1].trim()), listString[i + 2], listString[i + 3].replace("\r", "")));
        }
        return list;
    }

    //for testing purposes
    @Override
    public String toString() {
        String s = "";
        for (Packet packet : packets) {
            s += packet + "\n----\n";
        }
        return s;
    }
}
