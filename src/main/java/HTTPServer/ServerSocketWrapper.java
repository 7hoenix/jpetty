package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by jphoenix on 8/1/16.
 */
public class ServerSocketWrapper implements ServerConnectible {
    private Connectible socket;

    public ServerSocketWrapper(Connectible socket) throws IOException {
        this.socket = socket;
    }

    public ServerSocketWrapper() throws IOException {
        this.socket = new SocketWrapper(new ServerSocket(5000).accept());
    }

    public Connectible accept() throws IOException {
        return socket;
    }

    public void close() {}
}
