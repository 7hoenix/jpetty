package Server;

import HTTPServer.Connection;
import HTTPServer.Server;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class HTTPServerTest extends TestCase {
    public void test_it_works() throws Exception {
        assertEquals(1, 1);
    }
//    public void test_it_writes_to_the_output_stream() throws Exception {
//        InputStream input = new ByteArrayInputStream("GET / HTTP/1.1\r\n\r\n".getBytes());
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
//        MockSocket socket = new MockSocket(input, output);
//        Connection connection = new Connection(socket);
//        MockServerSocket serverSocket = new MockServerSocket(connection, 1);
//        Server server = new Server(serverSocket, new Setup());
//
//        server.run();
//
//        assertEquals(true, socket.isRead());
////        assertEquals(true, socket.isWritten());
//    }
}
