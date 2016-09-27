package Server;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;
import junit.framework.TestCase;

public class RouterTest extends TestCase {
    public void test_it_can_add_a_route() throws Exception {
        Handler handler = new MockHandler(200);

        Router router = new Router().add("/", "GET", handler);

        assertEquals(handler, router.getHandler(new Request("/", "GET")));
    }

    public void test_it_can_assign_multiple_routes_if_handed_an_array_of_actions() throws Exception {
        Handler handler = new MockHandler(200);
        String[] actions = new String[] {"GET", "POST", "OPTIONS"};

        Router router = new Router().add("/", actions, handler);

        assertEquals(handler, router.getHandler(new Request("/", "GET")));
        assertEquals(handler, router.getHandler(new Request("/", "POST")));
        assertEquals(handler, router.getHandler(new Request("/", "OPTIONS")));
    }

    public void test_it_returns_error_handler_with_404_if_route_and_resource_is_not_found() throws Exception {
        Router router = new Router();

        Response response = router.route(new Request("/cake", "GET"));

        assertEquals(404, response.getStatusCode());
    }

    public void test_it_returns_a_listing_of_the_methods_that_will_work_on_a_resource() throws Exception {
        Request request = new Request("/method_options", "OPTIONS");
        Router router = new Router()
                .add("/method_options", new String[] {"GET", "HEAD", "POST", "PUT"}, new MockHandler(200));

        Response response = router.route(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("HEAD,POST,GET,PUT,OPTIONS", response.getHeader("Allow"));
    }

    public void test_it_returns_options_by_default() throws Exception {
        Request request = new Request("/method_options2", "OPTIONS");
        Router router = new Router().add("/method_options2", "PUT", new MockHandler(200));

        Response response = router.route(request);

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
