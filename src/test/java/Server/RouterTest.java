package Server;

import CobSpecApp.MockHandler;
import CobSpecApp.Handler;
import HTTPServer.Request;
import HTTPServer.Router;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.util.HashMap;

public class RouterTest extends TestCase {
    public void test_it_routes_to_the_expected_handler() throws Exception {
        HashMap supportedRoutes = new HashMap();
        MockHandler mockHandler = new MockHandler(new Setup(new String[0]));
        supportedRoutes.put("GET", mockHandler);
        HashMap params = new HashMap();
        params.put("action", "GET");
        Router router = new Router(supportedRoutes);

        Handler handler = router.route(new Request(params));

        assertEquals(handler, mockHandler);
    }

    public void test_it_returns_the_error_handler_with_404_if_route_is_not_found() throws Exception {
        HashMap supportedRoutes = new HashMap();
        HashMap params = new HashMap();
        params.put("action", "TRACE");
        Router router = new Router(supportedRoutes);

        Handler handler = router.route(new Request(params));

        assertEquals("HTTP/1.1 405 NOT FOUND\r\n", new String(handler.handle(new Request(params)).getHeader(), "UTF-8"));
    }
}
