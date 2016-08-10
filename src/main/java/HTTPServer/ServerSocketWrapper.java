package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by jphoenix on 8/1/16.
 */
public class ServerSocketWrapper implements ServerConnectible {
    private ServerSocket serverSocket;
    private Integer connectionCount = 0;

    public ServerSocketWrapper(Integer connectionCount) throws IOException {
        this.serverSocket = new ServerSocket(5000);
        this.connectionCount = connectionCount;
    }

    public ServerSocketWrapper(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Connectible accept() throws IOException {
        connectionCount++;
        return new SocketWrapper(serverSocket.accept());
    }

    public Boolean listening() {
        return true;
    }

    public Integer connectionCount() {
        return connectionCount;
    }

    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
