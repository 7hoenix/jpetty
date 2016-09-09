package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.RequestParser;
import HTTPServer.Response;
import HTTPServer.Settings;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;

public class PutHandlerTest extends TestCase {
    public void test_it_passes_the_simple_put_spec() throws Exception {
        Request request = new RequestParser().parse(new ByteArrayInputStream("PUT /games/1 HTTP/1.1\r\n\r\n".getBytes()));
        PutHandler handler = new PutHandler(new Settings(new String[0]));

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
    }
}
