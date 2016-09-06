package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private ServerConnectable serverConnection;
    private Setup settings;
    private DataStorage dataStore;

    public static void main(String[] args) throws IOException {
        Setup settings = new Setup(args);
        ServerSocket serverSocket = new ServerSocket(settings.getPort());
        ServerConnectable serverConnection = new ServerSocketWrapper(serverSocket);
        Server server = new Server(serverConnection, settings);
        server.run();
    }

    public Server(ServerConnectable serverConnection, Setup settings) {
        this.serverConnection = serverConnection;
        this.settings = settings;
        this.dataStore = new DataStore();
    }

    public Server(ServerConnectable serverConnection, String[] args) {
        this.serverConnection = serverConnection;
        this.settings = new Setup(args);
        this.dataStore = new DataStore();
    }

    public void run() throws IOException {
        while (serverConnection.isListening() == true) {
            Connection connection = serverConnection.accept();
            Request request = connection.read();
            Response response = new BasicHandler(settings, dataStore).handle(request);
            connection.write(response);
            connection.close();
        }
        serverConnection.close();
    }
}
