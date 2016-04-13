//Author: John Madsen
package pkg490final.network.sender;

import java.io.IOException;
import java.net.SocketException;

/**
 * State of the sender client when it is currently sending a packet with
 * sequence number 1. The client sets the current packet to seq 1 and sends it,
 * then changes state to wait for ACK 1.
 *
 * @author John
 */
public class SenderSendState1 implements SenderState {

    @Override
    public void action(RDT30Sender wrapper) throws SocketException, IOException, InterruptedException {
        wrapper.getCurrentPacket().setSeqNumber(1);
        wrapper.rdtSend();
        wrapper.setState(new SenderWaitState1());
    }
}
