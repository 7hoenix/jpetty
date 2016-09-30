package HTTPServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WrappedSocket implements Connectable {
    private java.net.Socket socket;

    public WrappedSocket(java.net.Socket socket) {
        this.socket = socket;
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public void close() throws IOException {
        socket.close();
    }
}
