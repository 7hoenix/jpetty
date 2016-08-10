import HTTPServer.Connectible;
import HTTPServer.SocketWrapper;
import junit.framework.TestCase;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by jphoenix on 8/2/16.
 */
public class SocketWrapperTest extends TestCase {
    public void testItTakesAnInputStreamAndReturnsAString() throws Exception {
        String someStr = "hey there cake 1234";
        Socket socket = new Socket();
        InputStream streamIn = new ByteArrayInputStream(someStr.getBytes(StandardCharsets.UTF_8));
        OutputStream streamOut = new ByteArrayOutputStream();
        Connectible wrappedSocket = new SocketWrapper(socket, streamIn, streamOut);

        String request = wrappedSocket.read();

        assertEquals("hey there cake 1234\r\n", request);
    }

    public void testWriteTakesAStringAndWritesToAnOutputStream() throws Exception {
        String someStr = "hey there cake 1234";
        Socket socket = new Socket();
        InputStream streamIn = new ByteArrayInputStream(someStr.getBytes(StandardCharsets.UTF_8));
        OutputStream outputStream = new ByteArrayOutputStream();
        Connectible wrappedSocket = new SocketWrapper(socket, streamIn, outputStream);

        wrappedSocket.write(someStr);

        assertEquals("hey there cake 1234", outputStream.toString());
    }
}
