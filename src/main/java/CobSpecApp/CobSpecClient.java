package CobSpecApp;

import HTTPServer.ApplicationBuilder;
import HTTPServer.ConnectionManager;
import HTTPServer.Handler;
import HTTPServer.Middlewares.AuthorizationHandler;
import HTTPServer.Middlewares.LogHandler;
import HTTPServer.Middlewares.StaticFileHandler;
import HTTPServer.Repository;
import HTTPServer.Router;
import HTTPServer.Server;
import HTTPServer.WrappedServerSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class CobSpecClient {
    private final Server server;

    public static void main(String[] args) throws IOException {
        Settings settings = new Settings(args);
        Repository dataStore = new DataStore();
        ArrayList<String> log = new ArrayList<>();
        Handler application = ApplicationBuilder.handler(
                CobSpecRoutes.generate(new Router(), dataStore))
                .use(new LogHandler()
                        .setLog(log))
                .use(new AuthorizationHandler()
                        .setUserName("admin")
                        .setPassword("hunter2")
                        .setRealm("jphoenx personal server")
                        .setProtectedRoutes(new String[] {"/logs"}))
                .use(new StaticFileHandler()
                        .setPublicDirectory(settings.getRoot())
                        .setAutoIndex(settings.getAutoIndex()))
                .build();
        ServerSocket serverSocket = new ServerSocket(settings.getPort());
        ConnectionManager serverConnection = new WrappedServerSocket(serverSocket, application, log);
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
