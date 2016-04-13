//Author: John Madsen
package pkg490final;

/**
 * P2P file class used to store file names and sizes to be sent to a client or
 * server.
 *
 * @author John
 */
public class P2PFile {

    private String name;
    private long size;

    public P2PFile() {

    }

    public P2PFile(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void convertSpaces() { // space is replace by "|" char
        name = name.replace(" ", "|");
    }

    public void convertBack() { // "|" char is replace by space
        name = name.replace("|", " ");

    }

    @Override
    public String toString() {
        return "P2PFile{" + "name=" + name + ", size=" + size + "}\n";
    }

}
