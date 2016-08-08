package HTTPServer;

import java.io.IOException;

/**
 * Created by jphoenix on 8/1/16.
 */
public class Server {
    private ServerConnectible serverSocket;

    public static void main(String[] args) throws IOException {
        ServerConnectible serverSocket = new ServerSocketWrapper();
        Server server = new Server(serverSocket);
        server.run();
    }

    public Server(ServerConnectible serverSocket)
    {
        this.serverSocket = serverSocket;
    }

    public void run() throws IOException {
        Connectible socket = serverSocket.accept();
        System.out.println("connection accepted");
        String request = socket.read();
        HTTPRequestHandler handler = new HTTPRequestHandler();
        String response = handler.handle(request);
        socket.write(response);
    }
}
