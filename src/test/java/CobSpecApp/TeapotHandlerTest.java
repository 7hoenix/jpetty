package CobSpecApp;

import HTTPServer.*;
import junit.framework.TestCase;

public class TeapotHandlerTest extends TestCase {
    public void test_routes_to_a_418() throws Exception {
        Request request = new Request("/coffee", "GET");
        Handler handler = new TeapotHandler(new Setup(), new DataStore());

        Response response = handler.handle(request);

        assertEquals(418, response.getStatusCode());
        assertEquals("I'm a teapot", new String(response.getBody(), "UTF-8"));
    }

    public void test_it_responds_with_200_for_tea() throws Exception {
        Request request = new Request("/tea", "GET");
        Handler handler = new TeapotHandler(new Setup(), new DataStore());

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
    }
}
