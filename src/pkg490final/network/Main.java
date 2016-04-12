package pkg490final.network;

/**
 * Starts directory server and Client for test purposes.
 *
 * @author John
 */
public class Main {

    /**
     * if only running server or client then comment one out or run Client or
 Server class directly.
     */
    public static void main(String[] args) {


        //port forward 49000 & 33000
        String publicIPadddress = pkg490final.PacketUtilities.getPublicIP();

        pkg490final.network.Server.main(args);
        pkg490final.network.Client.start(publicIPadddress);

    }
}
