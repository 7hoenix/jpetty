package Server;

import HTTPServer.Connection;
import Server.StaticFileHandlers.MockHandler;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class WrappedServerSocketTest extends TestCase {
    public void test_it_can_accept_a_new_connection() throws Exception {
        MockSocket socket = new MockSocket(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());
        Connection connection = new Connection(socket, new MockHandler(200), new ArrayList<>());
        MockServerSocket serverSocket = new MockServerSocket(connection, 1);

        serverSocket.acceptConnection();

        assertEquals(false, serverSocket.isListening());
    }

    public void test_it_can_get_closed() throws Exception {
        MockSocket socket = new MockSocket(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream());
        Connection connection = new Connection(socket, new MockHandler(200), new ArrayList<>());
        MockServerSocket serverSocket = new MockServerSocket(connection, 1);

        serverSocket.close();

        assertEquals(false, serverSocket.isListening());
    }
}
