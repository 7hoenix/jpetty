package HTTPServer;

import CobSpecApp.CobSpecRoutes;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerConnectable serverConnection;

    public static void main(String[] args) throws IOException {
        Settings settings = new Settings(args);
        DataStorage dataStore = new DataStore();
        Router2 router = CobSpecRoutes.generate(new Router2(settings), settings, dataStore);
        Router2 updatedRouter = StaticRoutes.generate(router, settings, dataStore);

        ServerSocket serverSocket = new ServerSocket(settings.getPort());
        ServerConnectable serverConnection = new ServerSocketWrapper(serverSocket, updatedRouter);
        Server server = new Server(serverConnection);
        server.run();
    }

    public Server(ServerConnectable serverConnection) {
        this.serverConnection = serverConnection;
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
