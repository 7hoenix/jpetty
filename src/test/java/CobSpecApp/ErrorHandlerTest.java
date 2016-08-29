package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.Response;
import junit.framework.TestCase;

import java.util.HashMap;

public class ErrorHandlerTest extends TestCase {
    public void test_it_returns_an_error_response() throws Exception {
        ErrorHandler handler = new ErrorHandler("HTTP/1.1 404 NOT FOUND\r\n");

        Response response = handler.handle(new Request(new HashMap()));

        assertEquals("HTTP/1.1 404 NOT FOUND\r\n\r\n", new String(response.getFull(), "UTF-8"));
    }
}
