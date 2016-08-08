import HTTPServer.Connectible;
import HTTPServer.ServerConnectible;
import HTTPServer.ServerSocketWrapper;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 8/5/16.
 */
public class ServerSocketWrapperTest extends TestCase {
    public void testItReturnsAConnectible() throws Exception {
        Connectible socket = new MockSocket("cake");
        ServerConnectible serverWrapper = new ServerSocketWrapper(socket);

        Connectible newSocket = serverWrapper.accept();

        assertEquals(newSocket, socket);
    }
}
