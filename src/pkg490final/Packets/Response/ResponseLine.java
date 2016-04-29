package pkg490final.Packets.Response;

import pkg490final.Packets.Line;

/**
 * Response line for any response message made up of ResponseMethod.
 *
 * @author john
 */
public class ResponseLine extends Line {

    private ResponseMethod method;

    /**
     *  For creating a Response Line on the sending side.
     *
     * @param method: response Method to use.
     */
    public ResponseLine(ResponseMethod method) {
        this.method = method;
    }

    /**
     * Fpr reconstructing a ResponseLine on the receiving side.
     *
     * @param lineAttributes
     */
    public ResponseLine(String[] lineAttributes) {
        method = ResponseMethod.valueOf(ResponseMethod.class, lineAttributes[0]);
    }

    /**
     *
     * @return size that response line takes up in packet.
     */
    @Override
    public int size() {
        return method.name().length() + method.statusCode().length() + 7;
    }

    public ResponseMethod getMethod() {
        return method;
    }
    

    @Override
    public String toString() {
        return method.name() + " " + method.statusCode();
    }

    @Override
    public boolean isRequest() {
        return false;
    }

}
