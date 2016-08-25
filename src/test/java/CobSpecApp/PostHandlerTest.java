package CobSpecApp;

import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.util.HashMap;

public class PostHandlerTest extends TestCase {
    public void test_it_passes_the_simple_post_spec() throws Exception {
        HashMap params = new HashMap();
        params.put("action", "POST");
        params.put("path", "/games");
        PostHandler handler = new PostHandler(new Setup(new String[0]));

        Response response = handler.handle(params);

        assertEquals("HTTP/1.1 200 OK\r\n", new String(response.getHeader(), "UTF-8"));
    }
}
