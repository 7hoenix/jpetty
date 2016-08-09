import HTTPServer.HTTPRequestHandler;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 8/4/16.
 */
public class HTTPRequestHandlerTest extends TestCase {
    public void testItHandlesASimpleRequest() throws Exception {
        String request = "GET / HTTP/1.1";
        HTTPRequestHandler handler = new HTTPRequestHandler();

        String response = handler.handle(request);

        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" +
                "<!DOCTYPE html><html lang=\"en\"><body><h1>Hello World</h1></body></html>", response);
    }

    public void testItHandlesNotValid() throws Exception {
        String request = "GET /cake HTTP/1.1";
        HTTPRequestHandler handler = new HTTPRequestHandler();

        String response = handler.handle(request);

        assertEquals("HTTP/1.1 404 NOT FOUND\r\n\r\n", response);
    }
}
