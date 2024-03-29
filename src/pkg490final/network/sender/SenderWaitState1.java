//Author: John Madsen
package pkg490final.network.sender;

import java.io.IOException;
import java.net.SocketException;

/**
 * State of sender when it is waiting for ACK 1.
 *
 * @author John
 */
public class SenderWaitState1 implements SenderState {

    /**
     * waits for the correct ACK, if the correct ACK is received, the timer is
     * cancelled and the sender enters a new sendState1. If not the timer is
     * also cancelled and current packet is resent. the same state is then
     * reinitialized.
     *
     */
    @Override
    public void action(RDT30Sender wrapper) throws IOException, SocketException, InterruptedException {
        wrapper.senderPrint("Expecting ACK 1");
        if (wrapper.waitForACK() == 1) {
            wrapper.senderPrint("Received ACK 1");

            wrapper.cancelTimer();
            wrapper.incrementPacketsSent();
            wrapper.setState(new SenderSendState0());
        } else {
            wrapper.senderPrint("Received ACK 0");
            wrapper.senderPrint("\nWRONG SEQUENCE NUMBER RECEIVED, RESENDING PACKET\n-------\n");

            wrapper.cancelTimer();
            wrapper.rdtSend();
            wrapper.setState(new SenderWaitState1());
        }
    }
}
