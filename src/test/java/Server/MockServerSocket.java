package Server;

import HTTPServer.Connection;
import HTTPServer.ServerConnectable;

public class MockServerSocket implements ServerConnectable {
    public boolean listening = false;
    private Connection connection;
    private Integer connectionCount;

    public MockServerSocket(Connection connection, Integer connectionCount)
    {
        this.connection = connection;
        this.connectionCount = connectionCount;
    }

    @Override
    public Connection accept()
    {
        connectionCount--;
        listening = true;
        return connection;
    }

    @Override
    public boolean isListening() {
        if (connectionCount > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void close() {
        this.connectionCount = 0;
    }
}
