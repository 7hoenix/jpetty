package CobSpecApp;

import HTTPServer.*;
import junit.framework.TestCase;

public class HeadHandlerTest extends TestCase {
    public void test_it_returns_just_the_headers_with_no_body() throws Exception {
        Request request = new Request("HEAD", "/geoffs-sweet-site/samurai-champloo/board.gif");
        Handler handler = new HeadHandler(new Setup(), new DataStore());

        Response response = handler.handle(request);

        assertEquals("image/gif", response.getHeader("Content-Type"));
        assertEquals("898055", response.getHeader("Content-Length"));
        assertEquals(200, response.getStatusCode());
        assertEquals("", new String(response.getBody(), "UTF-8"));
    }
}
