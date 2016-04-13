//Author: John Madsen
package pkg490final.network;

import pkg490final.network.receiver.RDT20Receiver;

/**
 * Instance of Directory server to run. only one instance runs now,
 * multi-threaded in the future.
 *
 * @author John
 */
public class Server {

    /**
     * Creates a thread for the receiver and starts the receiver. with specified
     * port and name.
     *
     */
    public static void main(String[] args) {
        RDT20Receiver receiverThread = null;
        try {

            receiverThread = new RDT20Receiver("Receiver", 49000);
            receiverThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
