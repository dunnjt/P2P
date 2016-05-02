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

    try {
      // Start server
      P2PClient client = new P2PClient("Server", 7014, "test1.txt");
      P2PClient.setFileLocation("/Users/johndunn/Desktop/TCPTest/");
      client.start();
      client.sleep(1000);

      P2PHost host = new P2PHost("Host", 7014, "test1.txt");
      P2PHost.setFileLocation("/Users/johndunn/Desktop/p2p/");
      //host = new P2PHost("CLIENT1", 47000, "192.168.1.4", 33000, "test2.txt");
      //P2PClient client2 = new TCPClient("CLIENT2", 49000);
      host.start();
      //host.sleep(1000);
      //client2.start();
    } catch (Exception e) {
      e.printStackTrace();
                
    }
  }
}
