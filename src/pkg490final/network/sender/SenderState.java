//Author: John Madsen
package pkg490final.network.sender;

import java.io.IOException;
import java.net.SocketException;

/**
 * Interface for the state of the client sender.
 *
 * @author John
 */
public interface SenderState {

    /**
     * this method performs tasks based on which state the wrapper class is in.
     *
     * @param wrapper Sender class to be passed into the current state.
     */
    public void action(RDT30Sender wrapper) throws SocketException, IOException, InterruptedException;

}
