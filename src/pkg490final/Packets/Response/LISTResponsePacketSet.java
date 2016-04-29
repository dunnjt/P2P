//Author: John Madsen
package pkg490final.Packets.Response;

import java.util.ArrayList;
import pkg490final.P2PFile;
import pkg490final.PacketUtilities;

/**
 * Server returns query results to peer in the form of a list of P2P files.
 *
 * @author John
 */
public class LISTResponsePacketSet extends ResponsePacketSet{

    public LISTResponsePacketSet(ArrayList<P2PFile> queryResults) {
        super(new ResponseLine(ResponseMethod.LIST), PacketUtilities.p2pFilesToString(queryResults));
    }

}
