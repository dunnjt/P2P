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
<<<<<<< Updated upstream

=======
        RDT20Receiver receiverThread = null;
        try {
//             Start receiver -- comment this out on sender client
//            receiverThread = new RDT20Receiver("Receiver", 49000);
//            receiverThread.start();

            // Create sender --comment the rest of this out on receiver.
//            JOHN M public address. ports forwarded to desktop.
//            byte[] targetAdddress = {(byte) 96, (byte) 32, 16, (byte) 152};
//            receiverThread = new RDT20Receiver("Receiver", 49000);
//            receiverThread.start();

            // Create sender --comment the rest of this out on receiver.
//            JOHN M public address. ports forwarded to desktop.
//            byte[] targetAdddress = {(byte) 96, (byte) 32, 16, (byte) 152};
            byte[] targetAdddress = {(byte) 127, (byte) 0, 0, (byte) 1};
//            //JOHN DUNN public address.
//            byte[] targetAdddress = {(byte) 73, (byte) 159, 0, (byte) 71};
>>>>>>> Stashed changes

        //port forward 49000 & 33000
        String publicIPadddress = pkg490final.PacketUtilities.getPublicIP();

        pkg490final.network.Server.main(args);
        pkg490final.network.Client.start(publicIPadddress);

<<<<<<< Updated upstream
=======
            // Sleeping simply for demo visualization purposes
            Thread.sleep(10000);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
>>>>>>> Stashed changes
    }
}
