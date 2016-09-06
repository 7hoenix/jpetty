package HTTPServer;

import java.io.Closeable;
import java.io.IOException;

public class Connection implements Closeable {
    private Connectable socket;

    public Connection(Connectable socket) {
        this.socket = socket;
    }

    public Request read() throws IOException {
        return new RequestParser().parse(socket.getInputStream());
    }

    public void write(Response response) throws IOException {
        socket.getOutputStream().write(new ResponseFormatter().formatResponse(response));
    }

    public void close() throws IOException {
        socket.close();
    }
}
