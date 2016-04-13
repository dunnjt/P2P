//Author: John Madsen
package pkg490final.network;

/**
 * Starts directory server and Client for test purposes.
 * USES 127.0.0.1 LOOPBACK address.
 *
 * @author John
 */
public class MainLoopback {

    /**
     * Only use this loopback main if running server and client on one machine.
     * 
     */
    public static void main(String[] args) {

        String publicIPadddress = "127.0.0.1";

        pkg490final.network.Server.main(args);
        pkg490final.network.Client.start(publicIPadddress);

    }
}
