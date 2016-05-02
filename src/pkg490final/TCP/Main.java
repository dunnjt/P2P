/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.TCP;

/**
 * 
 * @author johndunn
 */
public class Main {

  public static void main(String[] args) {
    P2PClient client = null;
    P2PHost host = null;
    try {
      // Start server
      client = new P2PClient("Server", 33000, "test2.txt");
      client.start();
      client.sleep(1000);

      // Create client
      byte[] targetAdddress = {127, 0, 0, 1};
      host = new P2PHost("CLIENT1", 33000, "test2.txt");
      //P2PClient client2 = new TCPClient("CLIENT2", 49000);
      host.start();
      host.sleep(1000);
      //client2.start();
    } catch (Exception e) {
      e.printStackTrace();
                
    }
  }
}
