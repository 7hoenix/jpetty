package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.Response;
import HTTPServer.Settings;
import junit.framework.TestCase;

public class PostHandlerTest extends TestCase {
    public void test_it_passes_the_simple_post_spec() throws Exception {
        Request request = new Request("POST", "/games");
        PostHandler handler = new PostHandler(new Settings(new String[0]));

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
    }
}
