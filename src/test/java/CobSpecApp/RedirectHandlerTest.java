package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;
import junit.framework.TestCase;

import java.util.HashMap;

public class RedirectHandlerTest extends TestCase {
    public void test_it_handles_redirect() throws Exception {
        Request request = new Request("/redirect", "GET");
        Handler handler = new RedirectHandler(new Settings(new String[0]), new DataStore());

        Response response = handler.handle(request);

        assertEquals(302, response.getStatusCode());
        assertEquals("http://localhost:5000/", response.getHeader("Location"));
    }

    public void test_it_can_accept_a_map_which_overwrites_defaults() throws Exception {
        Request request = new Request("/redirect", "GET");
        HashMap<String, String> overwrite = new HashMap<>();
        overwrite.put("/redirect", "/cake");
        Handler handler = new RedirectHandler(new Settings(new String[0]), overwrite, new DataStore());

        Response response = handler.handle(request);

        assertEquals(302, response.getStatusCode());
        assertEquals("http://localhost:5000/cake", response.getHeader("Location"));
    }
}
