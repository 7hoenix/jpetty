package Server;

import HTTPServer.Connection;
import HTTPServer.ServerConnectable;

public class MockServerSocket implements ServerConnectable {
    private Connection connection;
    private boolean accepted = false;
    private Integer connectionCount;

    public MockServerSocket(Connection connection, Integer connectionCount)
    {
        this.connection = connection;
        this.connectionCount = connectionCount;
    }

    @Override
    public Connection accept()
    {
        this.accepted = true;
        connectionCount--;
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

    public boolean hasAccepted() {
        return accepted;
    }

    public void close() {
        this.connectionCount = 0;
    }
}
