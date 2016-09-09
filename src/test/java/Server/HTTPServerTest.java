package Server;

import HTTPServer.*;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HTTPServerTest extends TestCase {
    public void test_it_writes_to_the_output_stream() throws Exception {
        Connection connection = new OtherConnection(new MockSocket(new ByteArrayInputStream("".getBytes()), new ByteArrayOutputStream()));
        MockServerSocket serverSocket = new MockServerSocket(connection, 1);
        Server server = new Server(serverSocket);

        server.run();

        assertEquals(true, serverSocket.hasAccepted());
    }

    private class OtherConnection extends Connection {

        public OtherConnection(Connectable socket) {
            super(socket);
        }

        public void run() {
        }
    }
}
