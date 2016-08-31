package Server;

import CobSpecApp.MockHandler;
import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.RequestFactory;
import HTTPServer.Router;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.util.HashMap;

public class RouterTest extends TestCase {
    public void test_it_routes_to_the_expected_handler() throws Exception {
        Request request = new RequestFactory().create(new ByteArrayInputStream("GET / HTTP/1.1\r\n\r\n".getBytes()));
        HashMap supportedRoutes = new HashMap();
        MockHandler mockHandler = new MockHandler(new Setup(new String[0]));
        supportedRoutes.put("GET", mockHandler);
        Router router = new Router(supportedRoutes);

        Handler handler = router.route(request);

        assertEquals(handler, mockHandler);
    }

    public void test_it_returns_the_error_handler_with_404_if_route_is_not_found() throws Exception {
        Request request = new RequestFactory().create(new ByteArrayInputStream("TRACE / HTTP/1.1\r\n\r\n".getBytes()));
        HashMap supportedRoutes = new HashMap();
        Router router = new Router(supportedRoutes);

        Handler handler = router.route(request);

        assertEquals("HTTP/1.1 405 NOT FOUND\r\n", new String(handler.handle(request).getHeader(), "UTF-8"));
    }
}
