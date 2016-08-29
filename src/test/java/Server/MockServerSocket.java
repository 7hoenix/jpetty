package Server;

import HTTPServer.ServerConnectible;
import HTTPServer.Connectible;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MockServerSocket implements ServerConnectible {

    public boolean listening = false;
    private Connectible wrappedSocket;
    private Integer connectionCount;

    public MockServerSocket(Connectible wrappedSocket, Integer connectionCount)
    {
        this.wrappedSocket = wrappedSocket;
        this.connectionCount = connectionCount;
    }

    public MockServerSocket(Integer connectionCount)
    {
        InputStream input = new ByteArrayInputStream("GET / HTTP/1.1\r\n\r\n".getBytes());
        this.wrappedSocket = new MockSocket(input);
        this.connectionCount = connectionCount;
    }

    @Override
    public Connectible accept()
    {
        connectionCount++;
        listening = true;
        return wrappedSocket;
    }

    @Override
    public Boolean listening() {
        if (connectionCount < 0) {
            return true;
        } else {
            return false;
        }
    }

    public void close() {
        listening = false;
    }
}
