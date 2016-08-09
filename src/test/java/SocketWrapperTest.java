import HTTPServer.Connectible;
import HTTPServer.SocketWrapper;
import junit.framework.TestCase;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * Created by jphoenix on 8/2/16.
 */
public class SocketWrapperTest extends TestCase {
    public void testItTakesAnInputStreamAndReturnsAString() throws Exception {
        String someStr = "hey there cake 1234";
        InputStream streamIn = new ByteArrayInputStream(someStr.getBytes(StandardCharsets.UTF_8));
        OutputStream streamOut = new ByteArrayOutputStream();
        Connectible socket = new SocketWrapper(streamIn, streamOut);

        String request = socket.read();

        assertEquals("hey there cake 1234\r\n", request);
    }

    public void testWriteTakesAStringAndWritesToAnOutputStream() throws Exception {
        String someStr = "hey there cake 1234";
        InputStream streamIn = new ByteArrayInputStream(someStr.getBytes(StandardCharsets.UTF_8));
        OutputStream outputStream = new ByteArrayOutputStream();
        Connectible socket = new SocketWrapper(streamIn, outputStream);

        socket.write(someStr);

        assertEquals("hey there cake 1234", outputStream.toString());
    }
}
