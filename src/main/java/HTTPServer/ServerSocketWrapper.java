package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by jphoenix on 8/1/16.
 */
public class ServerSocketWrapper implements ServerConnectable {
    private ServerSocket serverSocket;

    public ServerSocketWrapper() throws IOException {
        serverSocket = new ServerSocket(5000);
    }

    public Connectible listenForRequest() throws IOException {
        return (Connectible) serverSocket.accept();
    }
}
