package HTTPServer;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jphoenix on 8/1/16.
 */
public class Server {
    private ServerConnectable serverSocket;

    public Server(ServerConnectable sSocket) {
        serverSocket = sSocket;
    }

    public Server() throws IOException {
        serverSocket = new ServerSocketWrapper();
    }

    public void run() throws IOException {
        Connectible socketWrapper = serverSocket.listenForRequest();
//        InputStream request = socketWrapper.getRequest();
//        String response = handleRequest(request);
//        socketWrapper.sendResponse(response);
    }

    public String handleRequest(String request) {
        return "HTTP/1.1 200 OK\nUser-Agent: ServerCake\nContent-Type: text/html\n\n" +
                "<html><body><h1>Hello world</h1></body></html>";
    }
}
