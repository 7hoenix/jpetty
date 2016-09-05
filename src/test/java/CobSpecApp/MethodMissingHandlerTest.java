package CobSpecApp;

import HTTPServer.*;
import junit.framework.TestCase;

public class MethodMissingHandlerTest extends TestCase {
    public void test_it_returns_method_not_allowed_response_if_not_allowed() throws Exception {
        Request request = new Request("PUT", "/file");
        Handler methodMissingHandler = new MethodMissingHandler(new Setup(), new DataStore());

        Response response = methodMissingHandler.handle(request);

        assertEquals(405, response.getStatusCode());
    }

    public void test_it_returns_method_not_allowed_response_for_post() throws Exception {
        Request request = new Request("POST", "/text-file.txt");
        Handler methodMissingHandler = new MethodMissingHandler(new Setup(), new DataStore());

        Response response = methodMissingHandler.handle(request);

        assertEquals(405, response.getStatusCode());
    }

    public void test_it_works_for_get_requests() throws Exception {
        Request request = new Request("GET", "/text-file.txt");
        Handler methodMissingHandler = new MethodMissingHandler(new Setup(), new DataStore());

        Response response = methodMissingHandler.handle(request);

        assertEquals(200, response.getStatusCode());
    }
}
