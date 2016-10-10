package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class WrappedServerSocket implements ConnectionManager {
    private ServerSocket serverSocket;
    private Handler handler;

    public WrappedServerSocket(ServerSocket serverSocket, Handler handler) {
        this.serverSocket = serverSocket;
        this.handler = handler;
    }

    public Connection acceptConnection() throws IOException {
        return new Connection(new WrappedSocket(serverSocket.accept()), handler);
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
