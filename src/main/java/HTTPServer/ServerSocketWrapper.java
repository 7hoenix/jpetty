package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWrapper implements ServerConnectable {
    private ServerSocket serverSocket;

    public ServerSocketWrapper(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Connection accept() throws IOException {
        return new Connection(new SocketWrapper(serverSocket.accept()));
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
