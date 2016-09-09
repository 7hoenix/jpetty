package Server;

import HTTPServer.*;
import junit.framework.TestCase;

public class Router2Test extends TestCase {
    public void test_it_can_add_a_route() throws Exception {
        Handler handler = new MockHandler(200);

        Router2 router = new Router2().add("/", "GET", handler);

        assertEquals(handler, router.getHandler(new Request("/", "GET")));
    }

    public void test_it_can_assign_multiple_routes_if_handed_an_array_of_actions() throws Exception {
        Handler handler = new MockHandler(200);
        String[] actions = new String[] {"GET", "POST", "OPTIONS"};

        Router2 router = new Router2().add("/", actions, handler);

        assertEquals(handler, router.getHandler(new Request("/", "GET")));
        assertEquals(handler, router.getHandler(new Request("/", "POST")));
        assertEquals(handler, router.getHandler(new Request("/", "OPTIONS")));
    }

    public void test_it_returns_error_handler_with_404_if_route_and_resource_is_not_found() throws Exception {
        Router2 router = new Router2();

        Response response = router.route(new Request("/cake", "GET"));

        assertEquals(404, response.getStatusCode());
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
