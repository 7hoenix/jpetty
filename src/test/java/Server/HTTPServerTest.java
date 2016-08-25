package Server;

import HTTPServer.Server;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HTTPServerTest extends TestCase {
    public void testItCanListenForAClient() throws Exception {
        MockServerSocket serverSocket = new MockServerSocket(-1);
        Server server = new Server(serverSocket, new String[0]);

        server.run();

        assertEquals(false, serverSocket.listening);
    }

    public void testItSendsBackTheProperResponse() throws Exception {
        InputStream request = new ByteArrayInputStream("GET / HTTP/1.1\r\nUser-Agent: Cake\r\nAccept-Language: en-us\r\n".getBytes());
        String properResponse = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\nConnection: close\r\nContent-Length: 71\r\n\r\n" +
                "<!DOCTYPE html><html lang=\"en\"><body><h1>Hello World</h1></body></html>";
        MockSocket socket = new MockSocket(request);
        MockServerSocket serverSocket = new MockServerSocket(socket, -1);
        String[] args = new String[1];
        args[0] = "-ai";
        Server server = new Server(serverSocket, args);

        server.run();

        assertEquals(properResponse, socket.displayValue());
    }
}
