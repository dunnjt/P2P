//Author: John Madsen
package pkg490final.Packets.Request;

import java.util.ArrayList;
import pkg490final.P2PFile;
import pkg490final.PacketUtilities;
import pkg490final.Packets.RequestLine;

/**
 * A packet set for an inform and update request. Takes in a list of p2p files
 * that are stored on the clients system and converts them to a String for the
 * packet body.
 *
 * @author John
 */
public class INFRequestPacketSet extends RequestPacketSet {

    public INFRequestPacketSet(ArrayList<P2PFile> localFiles, int sourcePort, String ip) {
        super(new RequestLine(RequestMethod.INF, sourcePort, ip), PacketUtilities.p2pFilesToString(localFiles));
    }

}
