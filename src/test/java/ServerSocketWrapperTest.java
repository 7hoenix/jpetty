import HTTPServer.ServerSocketWrapper;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 8/5/16.
 */
public class ServerSocketWrapperTest extends TestCase {
    public void testItReturnsAConnectible() throws Exception {
        MockSocket clientSocket = new MockSocket("cake");
        new Thread(clientSocket).start();
        ServerSocketWrapper wrappedServerSocket = new ServerSocketWrapper(-1);

        wrappedServerSocket.accept();

        assertEquals(0, (int) wrappedServerSocket.connectionCount());
    }
}
