/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.Packets.Response;

import pkg490final.Packets.Line;
import pkg490final.Packets.PacketSet;

/**
 * Parent class for all Response Packet Sets(LIST, OK, ACK, ERROR, SEND) 
 * 
 * @author john
 */
public class ResponsePacketSet extends PacketSet {

    public ResponsePacketSet(Line line, String data) {
        super(line, data);
    }
}
