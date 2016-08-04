import HTTPServer.SocketWrapper;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by jphoenix on 8/2/16.
 */
public class SocketWrapperTest extends TestCase {
    public void testItImplementsGetRequest() throws Exception {
        SocketWrapper socket = new SocketWrapper();

        String request = socket.getRequest();

        assertEquals("GET /", request);
    }

    public void testItImplementsSendResponse() throws Exception {
//        SocketWrapper socket = new SocketWrapper();
//
//        socket.sendResponse("<html>Hello world</html>");

        Socket socket = new Socket("localhost", 8383);

        String response = "GET /";
        byte[] bytes = response.getBytes();

        try (OutputStream out = socket.getOutputStream()) {
            out.write(bytes);
            System.out.println("hi");
        }
    }
}
