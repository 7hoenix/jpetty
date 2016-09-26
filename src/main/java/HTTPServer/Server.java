package HTTPServer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ConnectionManager serverConnection;

    public Server(ConnectionManager serverConnection) {
        this.serverConnection = serverConnection;
    }

    public void run() throws IOException {
        ExecutorService fixedPool = Executors.newFixedThreadPool(20);
        while (serverConnection.isListening() == true) {
            Connection connection = serverConnection.acceptConnection();
            fixedPool.submit(connection);
        }
        serverConnection.close();
    }
}
