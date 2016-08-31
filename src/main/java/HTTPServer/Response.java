package HTTPServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Response {
    private final byte[] header;
    private final byte[] body;

    public Response(byte[] header, byte[] body) {
        this.header = header;
        this.body = body;
    }

    public Response(byte[] header) {
        this.header = header;
        this.body = new byte[0];
    }

    public byte[] getFull() throws IOException {
        byte[] fullHeader = combine(getHeader(), "\r\n".getBytes());
        if (getBody() != null) {
            return combine(fullHeader, getBody());
        } else {
            return fullHeader;
        }
    }

    public byte[] getHeader() {
        return header;
    }

    public byte[] getBody() {
        return body;
    }

    private byte[] combine(byte[] original, byte[] addend) throws IOException {
        ByteArrayOutputStream combined = new ByteArrayOutputStream();
        combined.write(original);
        combined.write(addend);
        return combined.toByteArray();
    }
}
