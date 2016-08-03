import HTTPServer.Server;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 7/28/16.
 */
public class HttpServerTest extends TestCase {
    public void testItCanListenForAClient() throws Exception {
        MockServerSocket serverSocket = new MockServerSocket();
        Server server = new Server(serverSocket);

        server.run();

        assertEquals(true, serverSocket.listening);
    }

    public void testItSendsBackTheProperResponse() throws Exception {
        String request = "GET / HTTP/1.1\nUser-Agent: Cake\nAccept-Language: en-us\n";
        String properResponse = "HTTP/1.1 200 OK\nUser-Agent: ServerCake\nContent-Type: text/html\n\n" +
                "<html><body><h1>Hello world</h1></body></html>";
        MockSocket socket = new MockSocket(request);
        MockServerSocket serverSocket = new MockServerSocket(socket);
        Server server = new Server(serverSocket);

        server.run();

        String response = socket.getResponseValue();
        assertEquals(properResponse, response);
    }
}
