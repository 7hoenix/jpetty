package CobSpecApp;

import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.util.HashMap;

public class PutHandlerTest extends TestCase {
    public void test_it_passes_the_simple_put_spec() throws Exception {
        HashMap params = new HashMap();
        params.put("action", "PUT");
        params.put("path", "/games/1");
        PutHandler handler = new PutHandler(new Setup(new String[0]));

        Response response = handler.handle(params);

        assertEquals("HTTP/1.1 200 OK\r\n", new String(response.getHeader(), "UTF-8"));
    }
}
