import HTTPServer.Server;
import HTTPServer.ServerConnectible;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 7/28/16.
 */
public class HTTPServerTest extends TestCase {
    public void testItCanListenForAClient() throws Exception {
        MockServerSocket serverSocket = new MockServerSocket();
        Server server = new Server(serverSocket);

        server.run();

        assertEquals(true, serverSocket.listening);
    }

    public void testItSendsBackTheProperResponse() throws Exception {
        String request = "GET / HTTP/1.1\r\nUser-Agent: Cake\r\nAccept-Language: en-us\r\n";
        String properResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" +
                "<!DOCTYPE html><html lang=\"en\"><body><h1>Hello World</h1></body></html>";
        MockSocket socket = new MockSocket(request);
        ServerConnectible serverSocket = new MockServerSocket(socket);
        Server server = new Server(serverSocket);

        server.run();

        String response = socket.displayValue();
        assertEquals(properResponse, response);
    }
}