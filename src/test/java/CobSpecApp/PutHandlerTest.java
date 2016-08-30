package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.RequestFactory;
import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

public class PutHandlerTest extends TestCase {
    public void test_it_passes_the_simple_put_spec() throws Exception {
        Request request = new RequestFactory().create(new ByteArrayInputStream("PUT /games/1 HTTP/1.1\r\n\r\n".getBytes()));
        PutHandler handler = new PutHandler(new Setup(new String[0]));

        Response response = handler.handle(request);

        assertEquals("HTTP/1.1 200 OK\r\n", new String(response.getHeader(), "UTF-8"));
    }
}
