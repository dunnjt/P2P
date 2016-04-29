package pkg490final.Packets.Response;

/**
 * For creating an ACK Packet --not currently used.
 */
public class ACKResponsePacketSet extends ResponsePacketSet {

    public ACKResponsePacketSet() {
        super(new ResponseLine(ResponseMethod.ACK), "This Is an Ack packet");
    }

}
