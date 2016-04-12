package pkg490final.network.receiver;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * State of the receiver when next packet to be delivered should have Sequence
 * number 1
 *
 * @author john
 */
public class ReceiverState1 implements ReceiverState {

    int seq = 1;

    /**
     * Checks if the sequence number received is 1. if so delivers packet and
     * sends ACK 1. If not does not deliver and sends ACK 0
     *
     * @param wrapper receiver class passed in.
     */
    @Override
    public void action(RDT20Receiver wrapper) throws UnknownHostException, IOException {
        wrapper.receiverPrint("\n---------------------RECEIVER:\nExpecting SEQ " + seq);

        if (seq == wrapper.getCurrentPacket().getSeqNumber()) {
            wrapper.receiverPrint("Received seq " + seq);
            wrapper.sendACK(seq);
            
            wrapper.deliverData(wrapper.getCurrentPacket());
            wrapper.setState(new ReceiverState0());

        } else {
            wrapper.receiverPrint("Received seq " + 0);

            wrapper.sendACK(0);
        }
    }
}
