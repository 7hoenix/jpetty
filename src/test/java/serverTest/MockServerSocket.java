package serverTest;

import server.Connection;
import server.ConnectionManager;

public class MockServerSocket implements ConnectionManager {
    private Connection connection;
    private boolean accepted = false;
    private Integer connectionCount;

    public MockServerSocket(Connection connection, Integer connectionCount)
    {
        this.connection = connection;
        this.connectionCount = connectionCount;
    }

    @Override
    public Connection acceptConnection()
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
