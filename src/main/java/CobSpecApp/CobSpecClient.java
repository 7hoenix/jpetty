package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handler;
import HTTPServer.Middleware.AuthorizationHandler;
import HTTPServer.Middleware.LogHandler;
import HTTPServer.Middleware.StaticFileHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class CobSpecClient {
    private final Server server;

    public static void main(String[] args) throws IOException {
        Settings settings = new Settings(args);
        Repository dataStore = new DataStore();
        ArrayList<String> log = new ArrayList<>();
        Handler handler = CobSpecRoutes.generate(new Router(), dataStore);
        Handler updatedHandler = new LogHandler(handler)
                .setLog(log);
        Handler moarUpdatedHandler = new AuthorizationHandler(updatedHandler)
                .setUserName("admin")
                .setPassword("hunter2")
                .setRealm("jphoenx personal server")
                .setProtectedRoutes(new String[] {"/logs"});
        Handler superMoarUpdatedHandler = new StaticFileHandler(moarUpdatedHandler)
                .setPublicDirectory(settings.getRoot())
                .setAutoIndex(settings.getAutoIndex());
        ServerSocket serverSocket = new ServerSocket(settings.getPort());
        ConnectionManager serverConnection = new WrappedServerSocket(serverSocket, superMoarUpdatedHandler, log);
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
