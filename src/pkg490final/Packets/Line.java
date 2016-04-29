/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final.Packets;

/**
 * parent class of Request and Response Line to be stored in a packet set.
 *
 * @author john
 */
public abstract class Line {

    abstract public int size();
    abstract public boolean isRequest();



}
