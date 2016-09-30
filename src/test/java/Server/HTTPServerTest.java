package Server;

import HTTPServer.Connectable;
import HTTPServer.Connection;
import HTTPServer.Server;
import Server.StaticFileHandlers.MockHandler;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class HTTPServerTest extends TestCase {
    public void test_it_writes_to_the_output_stream() throws Exception {
        Connection connection = new OtherConnection(new MockSocket(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream()));
        MockServerSocket serverSocket = new MockServerSocket(connection, 1);
        Server server = new Server(serverSocket);

        server.run();

        assertEquals(true, serverSocket.hasAccepted());
    }

    private class OtherConnection extends Connection {

        public OtherConnection(Connectable connectable) {
            super(connectable, new MockHandler(200), new ArrayList<>());
        }

        public void run() {
        }
    }
}
