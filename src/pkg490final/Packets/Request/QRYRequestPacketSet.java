//Author: John Madsen
package pkg490final.Packets.Request;

import pkg490final.Packets.RequestLine;

/**
 * Query request packet set for a peer querying a server for songs matching their request.
 *
 * @author john m
 */
public class QRYRequestPacketSet extends RequestPacketSet{

    public QRYRequestPacketSet(String requestString, int sourcePort, String ip) {
        super(new RequestLine(RequestMethod.QRY, sourcePort, ip), requestString);
    }

}
