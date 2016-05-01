//Author: John Madsen
package pkg490final.Packets;

import java.util.ArrayList;
import java.util.Arrays;
import pkg490final.PacketUtilities;
import pkg490final.Packets.Response.ResponseLine;
import pkg490final.Packets.Response.ResponseMethod;

/**
 * Class to hold instance of data stored in one packet. toBytes outputs this
 * data in a String to be sent.
 *
 * @author John
 */
public class Packet {

    private Line line; //request/response line
    private String packetBody; //data held in packet
    private int seqNumber;
    private boolean lastPacket; //if its the last packet in the sequence.

    /**
     * Constructor for making packet on sender side.
     *
     * @param line Request/ResponseLine
     * @param packetBody data sent within packet(header lines)
     * @param lastPacket if this is the last packet in a set.
     */
    public Packet(Line line, String packetBody, boolean lastPacket) {
        this.line = line;
        this.packetBody = packetBody;
        this.lastPacket = lastPacket;
    }

    /**
     * Constructor for creating a packet object from received packet.
     *
     * @param receivedPacket: String of received packet.
     */
    
    public Packet(String receivedPacket) {
        String[] a = receivedPacket.split("\r\n\r\n");
        seqNumber = Character.getNumericValue(a[0].charAt(a[0].length() - 1));
        line = PacketUtilities.returnLine(a[0]);
        if (receivedPacket.charAt(receivedPacket.length() - 1) == '\u0004') {
            lastPacket = true;
            packetBody = a[1].substring(0, a[1].length() - 1);

        } else {
            lastPacket = false;
            packetBody = a[1];
        }
    }

    /**
     * @return: returns the proper response method related the response line. returns null if its a request method.
     */
    public ResponseMethod getResponseMethod() {
        try {
            ResponseLine rLine = (ResponseLine) line;
            return rLine.getMethod();

        } catch (Exception e) {
            return null;
        }
    }

    public Line getLine() {
        return line;
    }

    public String getPacketBody() {
        return packetBody;
    }

    public int getSeqNumber() {
        return seqNumber;
    }

    public boolean isLastPacket() {
        return lastPacket;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }

    @Override
    public String toString() {
        if (!lastPacket) {
            return line.toString() + " " + seqNumber + "\r\n\r\n" + packetBody;
        } else {
            return line.toString() + " " + seqNumber + "\r\n\r\n" + packetBody + '\u0004';
        }
    }

    public byte[] toBytes() {
        return toString().getBytes();
    }
}
