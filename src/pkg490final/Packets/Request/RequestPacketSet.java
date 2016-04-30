package pkg490final.Packets.Request;

import java.net.SocketException;
import pkg490final.Packets.Line;
import pkg490final.Packets.PacketSet;
import pkg490final.Packets.RequestLine;
import pkg490final.Packets.Response.ResponseMethod;
import pkg490final.network.receiver.ClientReceiver;

/**
 * Parent class for all Request Packet Sets(INF, QRY, EXT, REQ, DOWN)
 *
 * @author john
 */
public abstract class RequestPacketSet extends PacketSet {
    

    public RequestPacketSet(Line line, String data) {
        super(line, data);
    }
    
    public RequestPacketSet(){
        super();
    }
    
    public RequestMethod getRequestMethod(){
        RequestLine reqLine = (RequestLine)this.getLine();
        return reqLine.getMethod();
    }

//    public abstract ClientReceiver startReceivingThread(int receivingPort) throws SocketException;

    public abstract ResponseMethod responseExpected();
}
