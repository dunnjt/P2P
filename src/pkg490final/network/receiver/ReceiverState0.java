package pkg490final.network.receiver;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * State of the receiver when next packet to be delivered should have Sequence
 * number 0
 *
 * @author john
 */
public class ReceiverState0 implements ReceiverState {

    int seq = 0;

    /**
     * Checks if the sequence number received is 0. if so delivers packet and
     * sends ACK 0. If not does not deliver and sends ACK 1
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
            wrapper.setState(new ReceiverState1());
        } else {
            wrapper.receiverPrint("Received seq  " + 1);
            wrapper.sendACK(1);
        }

    }
}
