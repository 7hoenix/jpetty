package serverTest;

import junit.framework.TestCase;
import server.Connectable;
import server.Connection;
import server.Server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ServerTest extends TestCase {
    public void test_it_writes_to_the_output_stream() throws Exception {
        Connection connection = new OtherConnection(new MockSocket(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream()));
        MockServerSocket serverSocket = new MockServerSocket(connection, 1);
        Server server = new Server(serverSocket);

        server.run();

        assertEquals(true, serverSocket.hasAccepted());
    }

    private class OtherConnection extends Connection {

        public OtherConnection(Connectable connectable) {
            super(connectable, new MockHandler(200));
        }

        public void run() {
        }
    }
}
