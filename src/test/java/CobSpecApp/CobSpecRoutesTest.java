package CobSpecApp;

import HTTPServer.*;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.util.Map;

public class CobSpecRoutesTest extends TestCase {
    public void test_it_can_generate_a_map_of_routes_pointing_at_handlers() throws Exception {
        Request request = new Request("GET", "/");
        Map routes = CobSpecRoutes.generate(new Setup(new String[0]), new DataStore());
        Handler handler = (Handler) routes.get("GET");

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
    }
}
