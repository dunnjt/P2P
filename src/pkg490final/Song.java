/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg490final;

/**
 * An awesome and completely usable class
 * @author johndunn
 */
public class Song {
    private String title, artist, IP;
    private int size;
    
    public Song(String title, String artist, int size, String IP) {
        this.title = title;
        this.artist = artist;
        this.size = size;
        this.IP = IP;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getArtist() {
        return artist;
    }

    public int getSize() {
        return size;
    }
    
    public String getIP() {
        return IP;
    }
    
    public String toString() {
        return "Title: " + title + ", Artist: " + artist + ", Size: " + size + ", IP: " + IP;      
    }
}
