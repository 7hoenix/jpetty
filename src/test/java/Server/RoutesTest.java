package Server;

import CobSpecApp.MockHandler;
import HTTPServer.*;
import HTTPServer.RequestParser;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

public class RoutesTest extends TestCase {
    public void test_it_routes_to_the_expected_handler() throws Exception {
        Request request = new RequestParser().parse(new ByteArrayInputStream("GET / HTTP/1.1\r\n\r\n".getBytes()));
        HashMap supportedRoutes = new HashMap();
        MockHandler mockHandler = new MockHandler(new Settings(new String[0]));
        supportedRoutes.put("GET", mockHandler);
        Router router = new Router(supportedRoutes);

        Handler handler = router.route(request);

        assertEquals(handler, mockHandler);
    }

    public void test_it_returns_the_error_handler_with_404_if_route_is_not_found() throws Exception {
        Request request = new Request("GET", "/");
        HashMap supportedRoutes = new HashMap();
        Router router = new Router(supportedRoutes);

        Handler handler = router.route(request);

        assertEquals(404, handler.handle(request).getStatusCode());
    }
}
