package serverTest;

import junit.framework.TestCase;
import server.BasicHandler;
import server.Handler;
import server.Route;

public class RouteTest extends TestCase {
    public void test_it_can_return_a_handler_function() throws Exception {
        Handler handler = new BasicHandler();
        Route route = new Route()
                .withPath("/")
                .withAction("GET")
                .withHandler(handler);

        Handler handler2 = route.get("/", "GET");

        assertEquals(handler, handler2);
    }

    public void test_it_returns_null_if_path_does_not_match() throws Exception {
        Handler handler = new BasicHandler();
        Route route = new Route()
                .withPath("/")
                .withAction("GET")
                .withHandler(handler);

        Handler handler2 = route.get("/cake", "GET");

        assertEquals(handler2, null);
    }

    public void test_it_supports_wild_card_handling() throws Exception {
        Handler handler = new BasicHandler();
        Route route = new Route()
                .withPath("/.*")
                .withAction("GET")
                .withHandler(handler);

        Handler handler2 = route.get("/should-match", "GET");

        assertEquals(handler2, handler);
    }

    public void test_it_can_return_whether_the_path_matches_or_not() throws Exception {
        Handler handler = new BasicHandler();
        Route route = new Route()
                .withPath("/.*")
                .withAction("GET")
                .withHandler(handler);

        boolean result = route.isAMatch("/should-match", "GET");

        assertEquals(true, result);
    }

    public void test_it_can_get_path_and_action() throws Exception {
        Route route = new Route()
                .withPath("/hi")
                .withAction("OK")
                .withHandler(new BasicHandler());

        assertEquals("/hi", route.getPath());
        assertEquals("OK", route.getAction());
    }
}
