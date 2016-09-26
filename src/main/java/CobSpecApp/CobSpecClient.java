package CobSpecApp;

import HTTPServer.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class CobSpecClient {
    private final Server server;

    public static void main(String[] args) throws IOException {
        Settings settings = new Settings(args);
        Repository dataStore = new DataStore();
        ArrayList<String> log = new ArrayList<>();
        Router router = CobSpecRoutes.generate(new Router(settings, log), dataStore);
        ServerSocket serverSocket = new ServerSocket(settings.getPort());
        ConnectionManager serverConnection = new WrappedServerSocket(serverSocket, router, log);
        Server server = new Server(serverConnection);
        CobSpecClient client = new CobSpecClient(server);
        client.run();
    }

    public CobSpecClient(Server server) {
        this.server = server;
    }

    public void run() throws IOException {
        server.run();
    }
}
