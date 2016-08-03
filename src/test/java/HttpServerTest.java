import HTTPServer.Server;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jphoenix on 7/28/16.
 */
public class HttpServerTest extends TestCase {
    public void testItCanListenForAClient() throws Exception {
        FakeServerSocket serverSocket = new FakeServerSocket();
        Server server = new Server(serverSocket);

        server.run();

        assertEquals(true, serverSocket.listening);
    }

    public void testItCanHandleARequestFromAClient() throws Exception {
        FakeSocket socket = new FakeSocket();
        FakeServerSocket serverSocket = new FakeServerSocket(socket);
        Server server = new Server(serverSocket);

        server.run();

        assertEquals(true, serverSocket.listening);
    }

    public void testItSendsBackHelloWorldToRequestToSlash() throws Exception {
        String request = "GET / HTTP/1.1\nUser-Agent: Cake\nAccept-Language: en-us\n";
        InputStream input = new ByteArrayInputStream(request.getBytes());
        OutputStream output = new ByteArrayOutputStream();
        FakeSocket socket = new FakeSocket(input, output);
        FakeServerSocket serverSocket = new FakeServerSocket(socket);
        Server server = new Server(serverSocket);

        server.run();

    }

    public void testItSendsBackTheProperResponse() throws Exception {
        String request = "GET / HTTP/1.1\nUser-Agent: Cake\nAccept-Language: en-us\n";
        String expectedResponse = "HTTP/1.1 200 OK\nUser-Agent: ServerCake\nContent-Type: text/html\n\n" +
                "<html><body><h1>Hello world</h1></body></html>";
        InputStream input = new ByteArrayInputStream(request.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FakeSocket socket = new FakeSocket(input, output);
        FakeServerSocket serverSocket = new FakeServerSocket(socket);
        Server server = new Server(serverSocket);

        server.run();

        String response = output.toString();

        assertEquals(expectedResponse, response);
    }
}
