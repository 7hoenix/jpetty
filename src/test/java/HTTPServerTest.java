import HTTPServer.Server;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 7/28/16.
 */
public class HTTPServerTest extends TestCase {
    public void testItCanListenForAClient() throws Exception {
        MockServerSocket serverSocket = new MockServerSocket(-1);
        Server server = new Server(serverSocket, new String[0]);

        server.run();

        assertEquals(false, serverSocket.listening);
    }

    public void testItCanListenForTwoClients() throws Exception {
        MockServerSocket serverSocket = new MockServerSocket(-2);
        Server server = new Server(serverSocket, new String[0]);

        server.run();

        assertEquals(false, serverSocket.listening);
    }

    public void testItSendsBackTheProperResponse() throws Exception {
        String request = "GET / HTTP/1.1\r\nUser-Agent: Cake\r\nAccept-Language: en-us\r\n";
        String properResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" +
                "<!DOCTYPE html><html lang=\"en\"><body><h1>Hello World</h1></body></html>";
        MockSocket socket = new MockSocket(request);
        MockServerSocket serverSocket = new MockServerSocket(socket, -1);
        Server server = new Server(serverSocket, new String[0]);

        server.run();

        String response = socket.displayValue();
        assertEquals(properResponse, response);
    }
}
