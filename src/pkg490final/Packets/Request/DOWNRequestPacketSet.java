//Author: John Madsen
package pkg490final.Packets.Request;

import java.util.ArrayList;
import pkg490final.IOFunctions;
import pkg490final.P2PFile;
import pkg490final.PacketUtilities;
import pkg490final.Packets.Line;
import pkg490final.Packets.RequestLine;

public class DOWNRequestPacketSet extends RequestPacketSet{

    public DOWNRequestPacketSet(ArrayList<P2PFile> requestedFile, int sourcePort, String ip) {
        super(new RequestLine(RequestMethod.DOWN, sourcePort, ip), PacketUtilities.p2pFilesToString(requestedFile));        
    }

}
