package Server;

import HTTPServer.*;
import junit.framework.TestCase;

public class Router2Test extends TestCase {
    public void test_it_can_add_a_route() throws Exception {
        Handler handler = new MockHandler(200);

        Router2 router = new Router2().add("/", "GET", handler);

        assertEquals(handler, router.get("/", "GET"));
    }

    public void test_it_can_assign_multiple_routes_if_handed_an_array_of_actions() throws Exception {
        Handler handler = new MockHandler(200);
        String[] actions = new String[] {"GET", "POST", "OPTIONS"};

        Router2 router = new Router2().add("/", actions, handler);

        assertEquals(handler, router.get("/", "GET"));
        assertEquals(handler, router.get("/", "POST"));
        assertEquals(handler, router.get("/", "OPTIONS"));
    }

    public void test_it_can_handle_a_request_by_routing_it_to_the_proper_route() throws Exception {
        Router2 router = new Router2()
                .add("/", "GET", new MockHandler(200));
        Request request = new Request("/", "GET");

        Response response = router.route(request);

        assertEquals(200, response.getStatusCode());
    }

    public void test_it_returns_error_handler_with_404_if_route_is_not_found() throws Exception {
        Router2 router = new Router2();
        Request request = new Request("/", "GET");

        Response response = router.route(request);

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
