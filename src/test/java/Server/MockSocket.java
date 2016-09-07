package Server;

import HTTPServer.Connectable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MockSocket implements Connectable {
    private boolean open = true;
    private boolean read = false;
    private boolean written = false;
    private InputStream input;
    private OutputStream output;

    public MockSocket(InputStream input, OutputStream output) {
        this.input = input;
        this.output = output;
    }

    public InputStream getInputStream() {
        this.read = true;
        return input;
    }

    public OutputStream getOutputStream() throws IOException {
        this.written = true;
        return output;
    }

    public boolean isOpen() {
        return this.open;
    }

    public boolean isRead() {
        return this.read;
    }

    public boolean isWritten() {
        return this.written;
    }

    public void close() {
        this.open = false;
    }
}
