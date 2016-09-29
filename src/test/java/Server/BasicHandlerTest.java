package Server;

import HTTPServer.*;
import HTTPServer.Handler;
import HTTPServer.BasicHandler;
import junit.framework.TestCase;

public class BasicHandlerTest extends TestCase {
    public void test_it_handles_not_valid() throws Exception {
        Request request = new Request("/cake", "GET");
        Handler handler = new BasicHandler();

        Response response = handler.handle(request);

        assertEquals(404, response.getStatusCode());
    }
}
