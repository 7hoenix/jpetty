package HTTPServer;

import java.io.IOException;

/**
 * Created by jphoenix on 8/1/16.
 */
public class Server {
    private ServerConnectible serverSocket;
    private Setup settings;

    public static void main(String[] args) throws IOException {
        ServerConnectible serverSocket = new ServerSocketWrapper();
        Server server = new Server(serverSocket, args);
        server.run();
    }

    public Server(ServerConnectible serverSocket, String[] args)
    {
        this.serverSocket = serverSocket;
        this.settings = new Setup(args);
    }

    public void run() throws IOException {
        Connectible socket = serverSocket.accept();
        System.out.println("connection accepted");
        String request = socket.read();
        HTTPRequestHandler handler = new HTTPRequestHandler(settings);
        String response = handler.handle(request);
        socket.write(response);
    }
}
