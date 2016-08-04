import HTTPServer.SocketWrapper;
import com.oracle.tools.packager.IOUtils;
import junit.framework.TestCase;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by jphoenix on 8/2/16.
 */
public class SocketWrapperTest extends TestCase {
    public void testItTakesAnInputStreamAndReturnsAString() throws Exception {
        String arbitaryString = "hey there cake 1234";
        InputStream stream = new ByteArrayInputStream(arbitaryString.getBytes(StandardCharsets.UTF_8));
        SocketWrapper socket = new SocketWrapper(stream);

        String request = socket.read();

        assertEquals("hey there cake 1234", request);
    }
}
