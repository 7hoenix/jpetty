package HTTPServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;

/**
 * Created by jphoenix on 8/1/16.
 */
public class Server {
    private ServerConnectible wrappedServerSocket;
    private Setup settings;

    public static void main(String[] args) throws IOException {
        Setup settings = new Setup(args);
        ServerSocket serverSocket = new ServerSocket(settings.port);
        ServerConnectible wrappedServerSocket = new ServerSocketWrapper(serverSocket);
        Server server = new Server(wrappedServerSocket, settings);
        server.run();
    }

    public Server(ServerConnectible serverSocket, Setup settings) {
        this.wrappedServerSocket = serverSocket;
        this.settings = settings;
    }

    public Server(ServerConnectible serverSocket, String[] args) {
        this.wrappedServerSocket = serverSocket;
        this.settings = new Setup(args);
    }

    public void run() throws IOException {
        while (wrappedServerSocket.listening().equals(true)) {
            Connectible wrappedSocket = wrappedServerSocket.accept();
            InputStream inputStream = wrappedSocket.getInputStream();
            HTTPService service = new HTTPService(settings);
            byte[] output = service.processInput(inputStream);
            wrappedSocket.write(output);
            wrappedSocket.close();
        }
        wrappedServerSocket.close();
    }
}
