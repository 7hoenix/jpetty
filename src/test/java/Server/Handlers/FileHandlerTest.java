package Server.Handlers;

import HTTPServer.*;
import HTTPServer.Handlers.FileHandler;
import HTTPServer.Handlers.Handler;
import junit.framework.TestCase;

public class FileHandlerTest extends TestCase {
    public void test_it_handles_method_not_allowed_differently_for_different_file_types() throws Exception {
        Request getRequest = new Request("/thing.txt", "GET");
        Request putRequest = new Request("/thing.txt", "PUT");
        Request purgeRequest = new Request("/thing.txt", "PURGE");
        Handler handler = new FileHandler(new Settings(new String[] {"-d", "otherPublic"}), new Router());

        Response getResponse = handler.handle(getRequest);
        Response putResponse = handler.handle(putRequest);
        Response purgeResponse = handler.handle(purgeRequest);

        assertEquals(200, getResponse.getStatusCode());
        assertEquals(405, putResponse.getStatusCode());
        assertEquals(405, purgeResponse.getStatusCode());
    }
}
