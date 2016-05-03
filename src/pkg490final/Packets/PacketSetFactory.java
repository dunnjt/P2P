//Author: John Madsen
package pkg490final.Packets;

import java.util.ArrayList;
import pkg490final.Packets.Request.*;
import pkg490final.Packets.Response.*;

import pkg490final.Packets.Response.ResponseLine;
import pkg490final.Packets.Response.ResponseMethod;

/**
 * Factory class to take in an arraylist of packets received on the user and
 * return the correct packet set after building it. The packet set returned
 * could be any class that extends request or response packets.
 *
 * @author john
 */
public class PacketSetFactory {

    /**
     *
     * Takes in a array of packets and passes it to the correct method to create
     * a request or response packet.
     *
     * @param packets
     * @returns the correct packet set.
     */
    public static PacketSet createPacketSet(ArrayList<Packet> packets) {
        String data = "";
        for (Packet packet : packets) {
            data += packet.getPacketBody();
        }
        if (packets.get(0).getLine().isRequest()) {
            return createRequestPacketSet((RequestLine) packets.get(0).getLine(), data);
        } else {
            return createResponsePacketSet((ResponseLine) packets.get(0).getLine(), data);
        }
    }

    /**
     * For request packet sets
     *
     *
     * @param line request line
     * @param data packet data
     * @returns a specific request packet set.
     */
    public static RequestPacketSet createRequestPacketSet(RequestLine line, String data) {
        RequestPacketSet requestPacketSet;
        if (line.getMethod() == RequestMethod.QRY) {
            requestPacketSet = new QRYRequestPacketSet();
        } else if (line.getMethod() == RequestMethod.INF) {
            requestPacketSet = new INFRequestPacketSet();
        } else if (line.getMethod() == RequestMethod.EXT) {
            requestPacketSet = new EXTRequestPacketSet();
        } else if (line.getMethod() == RequestMethod.DOWN) {
            requestPacketSet = new DOWNRequestPacketSet();
        } else {
            return null;
        }
        requestPacketSet.setLine(line);
        requestPacketSet.setPacketBody(data);
        requestPacketSet.createPackets();
        return requestPacketSet;
    }

    /**
     * For response packet sets
     *
     *
     * @param line response line
     * @param data packet data
     * @returns a specific response packet set.
     */
    private static PacketSet createResponsePacketSet(ResponseLine line, String data) {
        ResponsePacketSet responsePacketSet;
        if (line.getMethod() == ResponseMethod.ACK) {
            responsePacketSet = new ACKResponsePacketSet();
        } else if (line.getMethod() == ResponseMethod.ERROR) {
            responsePacketSet = new ERRORResponsePacketSet();
        } else if (line.getMethod() == ResponseMethod.LIST) {
            responsePacketSet = new LISTResponsePacketSet();
        } else if (line.getMethod() == ResponseMethod.OK) {
            responsePacketSet = new OKResponsePacketSet();
        } else {
            return null;
        }
        responsePacketSet.setLine(line);
        responsePacketSet.setPacketBody(data);
        responsePacketSet.createPackets();
        return responsePacketSet;
    }

}
