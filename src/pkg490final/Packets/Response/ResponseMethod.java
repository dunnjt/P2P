package pkg490final.Packets.Response;

/**
 * Enum for all possible Response methods and corresponding status codes for
 * response lines.
 *
 * @author john
 */
public enum ResponseMethod {

    LIST(100),
    OK(200),
    ACK(300),
    ERROR(400),
    SEND(500);

    private final int statusCode;

    ResponseMethod(int statusCode) {
        this.statusCode = statusCode;
    }

    public String statusCode() {
        return String.valueOf(statusCode);
    }
}
