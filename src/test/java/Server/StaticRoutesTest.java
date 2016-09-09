package Server;

import HTTPServer.*;
import junit.framework.TestCase;

public class StaticRoutesTest extends TestCase {
    public void test_it_generates_a_listing_of_all_files() throws Exception {
        Router2 router = new Router2();
        Settings settings = new Settings(new String[] {"-d", "otherPublic"});
        Router2 updatedRouter = StaticRoutes.generate(router, settings, new DataStore());
        Request request = new Request("/", "GET");

        Response response = updatedRouter.route(request);

        assertEquals(404, response.getStatusCode());
    }
}
