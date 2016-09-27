package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;
import junit.framework.TestCase;

import java.util.HashMap;

public class RedirectHandlerTest extends TestCase {
    public void test_it_can_take_a_map_of_resources_and_redirections() throws Exception {
        Request request = new Request("/redirect", "GET");
        HashMap<String, String> redirections = new HashMap<>();
        redirections.put("/redirect", "http://localhost:5000/cake");
        Handler handler = new RedirectHandler(redirections);

        Response response = handler.handle(request);

        assertEquals(302, response.getStatusCode());
        assertEquals("http://localhost:5000/cake", response.getHeader("Location"));
    }
}
