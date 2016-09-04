package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.Response;
import junit.framework.TestCase;

public class ErrorHandlerTest extends TestCase {
    public void test_it_returns_an_error_response() throws Exception {
        ErrorHandler handler = new ErrorHandler(404);

        Response response = handler.handle(new Request("", ""));

        assertEquals(404, response.getStatusCode());
    }
}