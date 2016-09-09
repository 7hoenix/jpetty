package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWrapper implements ServerConnectable {
    private ServerSocket serverSocket;
    private Router2 router;

    public ServerSocketWrapper(ServerSocket serverSocket, Router2 router) {
        this.serverSocket = serverSocket;
        this.router = router;
    }

    public Connection accept() throws IOException {
        return new Connection(new SocketWrapper(serverSocket.accept()), router);
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
