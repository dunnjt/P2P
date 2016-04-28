/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.network.receiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author johndunn
 */
public class ServerStart {
    
    private static Runnable server;
    
    public static void main(String[] args) {

        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        System.out.println("Directory Server Started @: " + formatter.format(now.getTime()));

        DirectoryServer server = DirectoryServer.getInstance();
        server.run();
    }   
}
