import HTTPServer.SocketWrapper;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 8/2/16.
 */
public class SocketWrapperTest extends TestCase {
    public void testItHasAnInputStream() throws Exception {
        SocketWrapper socket = new SocketWrapper();

        socket.connect(4444);
    }
}
