package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by jphoenix on 8/1/16.
 */
public class Server {
    private ServerConnectible wrappedServerSocket;
    private Setup settings;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        ServerConnectible wrappedServerSocket = new ServerSocketWrapper(serverSocket);
        Server server = new Server(wrappedServerSocket, args);
        server.run();
    }

    public Server(ServerConnectible serverSocket, String[] args)
    {
        this.wrappedServerSocket = serverSocket;
        this.settings = new Setup(args);
    }

    public void run() throws IOException {
        while (wrappedServerSocket.listening().equals(true)) {
            Connectible wrappedSocket = wrappedServerSocket.accept();
            String request = wrappedSocket.read();
            HTTPRequestHandler handler = new HTTPRequestHandler(settings);
            String response = handler.handle(request);
            wrappedSocket.write(response);
        }
        wrappedServerSocket.close();
    }
}
