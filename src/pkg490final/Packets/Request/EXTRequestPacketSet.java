//Author: John Madsen
package pkg490final.Packets.Request;

import pkg490final.Packets.RequestLine;

/**
 * Sent when a peer wants to exit the peer to peer application.
 *
 * @author John
 */
public class EXTRequestPacketSet extends RequestPacketSet{

    public EXTRequestPacketSet(int sourcePort, String ip) {
        super(new RequestLine(RequestMethod.EXT, sourcePort, ip), "");
    }

}
