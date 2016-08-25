package CobSpecApp;

import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class CobSpecRoutesTest extends TestCase {
    public void test_it_can_generate_a_map_of_routes_pointint_at_handlers() throws Exception {
        Map routes = CobSpecRoutes.generate(new Setup(new String[0]));
        Map params = new HashMap();
        params.put("action", "GET");
        params.put("path", "/");
        Handler handler = (Handler) routes.get("GET");

        Response response = handler.handle(params);

        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n", new String(response.getHeader(), "UTF-8"));
    }
}
