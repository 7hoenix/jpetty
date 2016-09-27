package Server;

import HTTPServer.Connection;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class WrappedServerSocketTest extends TestCase {
    public void test_it_can_accept_a_new_connection() throws Exception {
        Connection connection = new Connection(new MockSocket(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream()));
        MockServerSocket serverSocket = new MockServerSocket(connection, 1);

        serverSocket.acceptConnection();

        assertEquals(false, serverSocket.isListening());
    }

    public void test_it_can_get_closed() throws Exception {
        Connection connection = new Connection(new MockSocket(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream()));
        MockServerSocket serverSocket = new MockServerSocket(connection, 1);

        serverSocket.close();

        assertEquals(false, serverSocket.isListening());
    }
}
