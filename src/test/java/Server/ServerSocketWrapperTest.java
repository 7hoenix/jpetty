package Server;

import HTTPServer.ServerSocketWrapper;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ServerSocketWrapperTest extends TestCase {
    public void testItReturnsAConnectible() throws Exception {
        InputStream input = new ByteArrayInputStream("cake".getBytes());
        MockSocket clientSocket = new MockSocket(input);
        new Thread(clientSocket).start();
        ServerSocketWrapper wrappedServerSocket = new ServerSocketWrapper(-1);

        wrappedServerSocket.accept();

        assertEquals(0, (int) wrappedServerSocket.connectionCount());
    }
}
