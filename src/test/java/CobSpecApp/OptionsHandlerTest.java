package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.Response;
import junit.framework.TestCase;

public class OptionsHandlerTest extends TestCase {
    public void test_it_always_returns_ok() throws Exception {
        Request getRequest = new Request("/method_options", "GET");
        Request headRequest = new Request("/method_options", "HEAD");
        Request postRequest = new Request("/method_options", "POST");
        Request putRequest = new Request("/method_options", "PUT");
        OptionsHandler handler = new OptionsHandler();

        Response getResponse = handler.handle(getRequest);
        Response headResponse = handler.handle(headRequest);
        Response postResponse = handler.handle(postRequest);
        Response putResponse = handler.handle(putRequest);

        assertEquals(200, getResponse.getStatusCode());
        assertEquals(200, headResponse.getStatusCode());
        assertEquals(200, postResponse.getStatusCode());
        assertEquals(200, putResponse.getStatusCode());
    }
}
