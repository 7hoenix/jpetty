import HTTPServer.HTTPRequestHandler;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 8/4/16.
 */
public class HTTPRequestHandlerTest extends TestCase {
    public void testItHandlesASimpleRequest() throws Exception {
        String request = "GET /";
        HTTPRequestHandler handler = new HTTPRequestHandler();

        String response = handler.handle(request);

        assertEquals("HTTP/1.1 200 OK\rContent-Type: text/html\r\r" +
                "<html><body><h1>Hello world</h1></body></html>", response);
    }

    public void testItHandlesNotValid() throws Exception {
        String request = "GET /cake";
        HTTPRequestHandler handler = new HTTPRequestHandler();

        String response = handler.handle(request);

        assertEquals("HTTP/1.1 404 NOT FOUND\r\r", response);
    }
}
