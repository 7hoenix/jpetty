package Server;

import HTTPServer.HTTPService;
import junit.framework.TestCase;

import java.io.*;

public class HTTPServiceTest extends TestCase {
    public void test_it_handles_not_valid() throws Exception {
        InputStream input = new ByteArrayInputStream("GET /cake HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        byte[] output = service.processInput(input);

        assertEquals("HTTP/1.1 404 NOT FOUND\r\n\r\n", new String(output, "UTF-8"));
    }
}
