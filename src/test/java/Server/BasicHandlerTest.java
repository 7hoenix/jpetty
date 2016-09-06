package Server;

import HTTPServer.BasicHandler;
import HTTPServer.Request;
import HTTPServer.Response;
import junit.framework.TestCase;

public class BasicHandlerTest extends TestCase {
    public void test_it_handles_not_valid() throws Exception {
        Request request = new Request("GET", "/cake");
        BasicHandler handler = new BasicHandler("public");

        Response response = handler.handle(request);

        assertEquals(404, response.getStatusCode());
    }
}
