//Author: John Madsen
package pkg490final.Packets.Response;

/**
 * Reason for being sent:
 * Peer - TCP file transfer commencing
 * Server - Peer added to master list from Inform and update request.
 *
 * @author John
 */
public class OKResponsePacketSet extends ResponsePacketSet{

    public OKResponsePacketSet() {
        super(new ResponseLine(ResponseMethod.OK), "");
    }

}
