/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.Packets;

import java.util.ArrayList;

/**
 * All the information held to make up numerous packets from one long string of
 * data and the request / response lines.
 *
 * @author john
 */
public class PacketSet {

    private Line line;
    public String data ="";
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

    /**
     * for creating packetSet from set of packets on receiver side
     *
     * @param packets received
     */
    public PacketSet(ArrayList<Packet> packets) {
        line = packets.get(0).getLine();
        this.packets = packets;
        for (Packet packet : packets) {
            data += packet.getPacketBody();
        }
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
        int bodyLength = 128 - getLine().size();

        while (counter < getPacketBody().length()) {
            if (counter + bodyLength > getPacketBody().length() + 1) {
                packets.add(new Packet(line, getPacketBody().substring(counter), false));
            } else {
                packets.add(new Packet(line, getPacketBody().substring(counter, counter + bodyLength), true));
            }
            counter += bodyLength;
        }
    }

    public void setPacketBody(String packetBody) {
        this.data = packetBody + "\u0004";
    }

    public String getPacketBody() {
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
