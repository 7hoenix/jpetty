package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerConnectable serverConnection;
    private Setup settings;

    public static void main(String[] args) throws IOException {
        Setup settings = new Setup(args);
        ServerSocket serverSocket = new ServerSocket(settings.getPort());
        ServerConnectable serverConnection = new ServerSocketWrapper(serverSocket, settings);
        Server server = new Server(serverConnection, settings);
        server.run();
    }

    public Server(ServerConnectable serverConnection, Setup settings) {
        this.serverConnection = serverConnection;
        this.settings = settings;
    }

    public void run() throws IOException {
        ExecutorService fixedPool = Executors.newFixedThreadPool(20);
        while (serverConnection.isListening() == true) {
            Connection connection = serverConnection.accept();
            fixedPool.submit(connection);
        }
        serverConnection.close();
    }
}
