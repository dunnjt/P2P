package pkg490final.Packets;

/**
 * Request Line which is what makes up the first line of all request packets.
 *
 * @author john
 */
import java.net.Inet4Address;
import java.net.UnknownHostException;
import pkg490final.IOFunctions;
import pkg490final.Packets.Request.RequestMethod;

public class RequestLine extends Line {

    private RequestMethod method;
    private String hostName;
    private String ip;
    private int sourcePort;

    /**
     * For creating a RequestLine on the sender side. gets host name and public
     * IP as well.
     *
     * @param method: type of requestLine
     * @param sourcePort: port to contact client back on
     */
    public RequestLine(RequestMethod method, int sourcePort, String ip) {
        this.sourcePort = sourcePort;
        this.method = method;
        hostName = IOFunctions.getLocalHostName();
        this.ip = ip;

    }

    /**
     * For creating requestLine on receiver side from a packet.
     *
     * @param lineAttributes different attributes appearing on the first line of
     * a reconstructed packet
     */
    public RequestLine(String[] lineAttributes) {
        method = RequestMethod.valueOf(lineAttributes[0]);
        hostName = lineAttributes[1];
        ip = lineAttributes[2];
        sourcePort = Integer.parseInt(lineAttributes[3]);
    }

    /**
     * @return returns total size of request line.
     */
    @Override
    public int size() {
        return method.name().length() + hostName.length() + ip.length() + Integer.toString(sourcePort).length() + 9;
    }

    /**
     *
     * @returns Inet4Address of ip stored in requestLine.
     */
    public Inet4Address getIp() throws UnknownHostException {
        return (Inet4Address) Inet4Address.getByName(ip);
    }
    public String getIpString(){
        return ip;
    }

    public String getHostName() {
        return hostName;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public RequestMethod getMethod() {
        return method;
    }
    
    

    @Override
    public String toString() {

        return method.name() + " " + hostName + " " + ip + " " + sourcePort;
    }

    @Override
    public boolean isRequest() {
        return true;
    }
}
