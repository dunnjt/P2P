//Author: John Madsen
package pkg490final.Packets.Response;

import pkg490final.Packets.Line;

/**
 * Error is what is return when the file doesnt exist either from a query or
 * from a download request on a peer.
 *
 * @author John
 */
public class ERRORResponsePacketSet extends ResponsePacketSet {

    public ERRORResponsePacketSet() {
        super(new ResponseLine(ResponseMethod.LIST), "");
    }

//    public ERRORResponsePacketSet() {
//
//    }
}
