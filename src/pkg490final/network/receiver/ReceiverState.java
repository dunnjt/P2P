
package pkg490final.network.receiver;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Interface for the state of the receiver.
 *
 * @author john
 */
public interface ReceiverState {

    /**
     * this method performs different tasks based on which state the wrapper class is in.
     *
     * @param wrapper receiver wrapper class.
     */
    public void action (RDT20Receiver wrapper)  throws UnknownHostException, IOException;       
    
}