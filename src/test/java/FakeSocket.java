import HTTPServer.Connectable;

import java.io.*;

/**
 * Created by jphoenix on 8/1/16.
 */
public class FakeSocket implements Connectable {
    private Integer callsIn = 0;
    private Integer callsOut = 0;
    private InputStream input;
    private OutputStream output;

    public Integer callsIn() {
        return callsIn;
    }

    public Integer callsOut() {
        return callsOut;
    }

    public FakeSocket(InputStream inputStream, OutputStream outputStream) {
        input = inputStream;
        output = outputStream;
    }

    public FakeSocket() {
        input = new ByteArrayInputStream("cake".getBytes());
    }

    public InputStream getRequest() {
        callsIn++;
        return input;
    }

    public OutputStream giveResponse() {
        callsOut++;
        return output;
    }
}
