package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.RequestFactory;
import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;

public class PostHandlerTest extends TestCase {
    public void test_it_passes_the_simple_post_spec() throws Exception {
        Request request = new RequestFactory().create(new ByteArrayInputStream("POST /games HTTP/1.1\r\n\r\n".getBytes()));
        PostHandler handler = new PostHandler(new Setup(new String[0]));

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
    }
}
