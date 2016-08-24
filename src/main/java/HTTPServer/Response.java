package HTTPServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by jphoenix on 8/24/16.
 */
public class Response {
    private byte[] header;
    private byte[] body;

    public byte[] getFull() throws IOException {
        byte[] fullHeader = combine(getHeader(), "\r\n".getBytes());
        if (getBody() != null) {
            return combine(fullHeader, getBody());
        } else {
            return fullHeader;
        }
    }

    public void setHeader(byte[] header) {
        this.header = header;
    }

    public void setBody(byte[] body) {
        this.body = body;
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
