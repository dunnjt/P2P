/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.Packets.Response;

/**
 * For creating an ACK Packet --not currently used.
 */
public class ACKResponsePacketSet extends ResponsePacketSet {

    public ACKResponsePacketSet() {
        super(new ResponseLine(ResponseMethod.ACK), "This Is an Ack packet");
    }
    
    
    
}
