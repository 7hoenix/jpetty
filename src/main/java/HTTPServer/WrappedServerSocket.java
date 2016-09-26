package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class WrappedServerSocket implements ConnectionManager {
    private ServerSocket serverSocket;
    private Router router;
    private ArrayList<String> log;

    public WrappedServerSocket(ServerSocket serverSocket, Router router, ArrayList<String> log) {
        this.serverSocket = serverSocket;
        this.router = router;
        this.log = log;
    }

    public Connection acceptConnection() throws IOException {
        return new Connection(new WrappedSocket(serverSocket.accept()), router, log);
    }

    public boolean isListening() {
        return true;
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
