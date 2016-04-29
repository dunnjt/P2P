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
    P2PHost serverThread = null;
    P2PClient clientThread = null;
    try {
      // Start server
      serverThread = new P2PHost("Server", 49000);
      serverThread.start();

      // Create client
      byte[] targetAdddress = {127, 0, 0, 1};
      P2PClient client1 = new P2PClient("CLIENT1", 49000);
      //P2PClient client2 = new TCPClient("CLIENT2", 49000);
      client1.start();
      //client2.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
