package Server;

import HTTPServer.Connectible;
import HTTPServer.SocketWrapper;
import junit.framework.TestCase;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketWrapperTest extends TestCase {
    public void testItTakesAnInputStreamAndReturnsAString() throws Exception {
        String someStr = "hey there cake 1234";
        Socket socket = new Socket();
        InputStream streamIn = new ByteArrayInputStream(someStr.getBytes(StandardCharsets.UTF_8));
        OutputStream streamOut = new ByteArrayOutputStream();
        Connectible wrappedSocket = new SocketWrapper(socket, streamIn, streamOut);

        InputStream request = wrappedSocket.getInputStream();

        assertEquals("hey there cake 1234\r\n", convertStreamToString(request));
    }

    public void testWriteTakesAStringAndWritesToAnOutputStream() throws Exception {
        byte[] someStr = "hey there cake 1234".getBytes();
        Socket socket = new Socket();
        InputStream streamIn = new ByteArrayInputStream(someStr);
        OutputStream outputStream = new ByteArrayOutputStream();
        Connectible wrappedSocket = new SocketWrapper(socket, streamIn, outputStream);

        wrappedSocket.write(someStr);

        assertEquals("hey there cake 1234", outputStream.toString());
    }

    private String convertStreamToString(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            if (line.isEmpty()) break;
            out.append("\r\n");
        }
        return out.toString();
    }
}
