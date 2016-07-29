import Server.SocketDriver;
import Server.Server;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 7/28/16.
 */
public class HttpServerTest extends TestCase {
    public void testItCanAcceptARequest() throws Exception {
        MockSocket socket = new MockSocket();
        Server s = new Server(socket);

        s.run();

        assertEquals(1, (int) socket.inputCalls);
        assertEquals(1, (int) socket.outputCalls);
    }

    private class MockSocket implements SocketDriver {
        private Integer inputCalls = 0;
        private Integer outputCalls = 0;

        public void getInputStream() {
            inputCalls += 1;
        }

        public void getOutputStream() {
            outputCalls += 1;
                                            }
    }
}
