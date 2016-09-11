package Server.Handlers;

import HTTPServer.*;
import HTTPServer.Handlers.BasicHandler;
import HTTPServer.Handlers.Handler;
import junit.framework.TestCase;

import java.util.ArrayList;

public class BasicHandlerTest extends TestCase {
    public void test_it_handles_not_valid() throws Exception {
        Request request = new Request("/cake", "GET");
        Handler handler = new BasicHandler(new Settings(), new Router(), new ArrayList<>());

        Response response = handler.handle(request);

        assertEquals(404, response.getStatusCode());
    }
}
