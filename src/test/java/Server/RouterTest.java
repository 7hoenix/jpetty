package Server;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;
import HTTPServer.Router;
import junit.framework.TestCase;

public class RouterTest extends TestCase {
    public void test_it_can_add_a_route() throws Exception {
        Handler handler = new MockHandler(200);

        Router router = new Router().setRoute("/", "GET", handler);

        assertEquals(handler, router.getRoute(new Request("/", "GET")));
    }

    public void test_it_returns_error_handler_with_404_if_route_and_resource_is_not_found() throws Exception {
        Handler router = new Router();

        Response response = router.handle(new Request("/cake", "GET"));

        assertEquals(404, response.getStatusCode());
    }

    public void test_it_returns_a_listing_of_the_methods_that_will_work_on_a_resource() throws Exception {
        Request request = new Request("/method_options", "OPTIONS");
        Handler router = new Router()
                .setRoute("/method_options", "GET", new MockHandler(200))
                .setRoute("/method_options", "POST", new MockHandler(200))
                .setRoute("/method_options", "HEAD", new MockHandler(200));

        Response response = router.handle(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("GET,POST,HEAD,OPTIONS", response.getHeader("Allow"));
    }

    public void test_it_returns_options_by_default() throws Exception {
        Request request = new Request("/method_options2", "OPTIONS");
        Handler router = new Router()
                .setRoute("/method_options2", "PUT", new MockHandler(200));

        Response response = router.handle(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("PUT,OPTIONS", response.getHeader("Allow"));
    }

    private class MockHandler implements Handler {
        private int statusCode;

        public MockHandler(int statusCode) {
            this.statusCode = statusCode;
        }

        public Response handle(Request request) {
            return new Response(statusCode);
        }
    }
}
