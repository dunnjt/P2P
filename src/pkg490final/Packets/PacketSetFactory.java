//Author: John Madsen
package pkg490final.Packets;

import java.util.ArrayList;
import pkg490final.Packets.Request.*;
import pkg490final.Packets.Response.*;

import pkg490final.Packets.Response.ResponseLine;
import pkg490final.Packets.Response.ResponseMethod;

public class PacketSetFactory {

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
