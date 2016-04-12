package pkg490final.Packets.Request;

import pkg490final.Packets.Line;
import pkg490final.Packets.PacketSet;

/**
 * Parent class for all Request Packet Sets(INF, QRY, EXT, REQ, DOWN) 
 * 
 * @author john
 */
public class RequestPacketSet extends PacketSet {

    public RequestPacketSet(Line line, String data) {
        super(line, data);
    }

}
