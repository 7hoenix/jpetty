package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWrapper implements ServerConnectable {
    private ServerSocket serverSocket;
    private Setup settings;
    private DataStorage dataStore;

    public ServerSocketWrapper(ServerSocket serverSocket, Setup settings) {
        this.serverSocket = serverSocket;
        this.settings = settings;
        this.dataStore = new DataStore();
    }

    public Connection accept() throws IOException {
        return new Connection(new SocketWrapper(serverSocket.accept()), settings, dataStore);
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
