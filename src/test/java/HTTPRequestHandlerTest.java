import HTTPServer.HTTPRequestHandler;
import junit.framework.TestCase;

import java.io.*;

/**
 * Created by jphoenix on 8/4/16.
 */
public class HTTPRequestHandlerTest extends TestCase {
    public void testItHandlesASimpleRequest() throws Exception {
        String request = "GET / HTTP/1.1";
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        String response = handler.handle(request);

        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" +
                "<!DOCTYPE html><html lang=\"en\"><body><h1>Hello World</h1></body></html>", response);
    }

    public void testItHandlesNotValid() throws Exception {
        String request = "GET /cake HTTP/1.1";
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        String response = handler.handle(request);

        assertEquals("HTTP/1.1 404 NOT FOUND\r\n\r\n", response);
    }

    public void testItHandlesIndexesAsSlashes() throws Exception {
        String request = "GET /brians HTTP/1.1";
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        String response = handler.handle(request);

        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" +
                "<!DOCTYPE html><html lang=\"en\"><body>" +
                "<a href=\"http://localhost:5000/brians/index.html\">index.html</a>\r\n" +
                "<a href=\"http://localhost:5000/brians/ping-pong-equipment\">ping-pong-equipment</a>\r\n" +
                "</body></html>", response);
    }

    public void testItReturnsAListingOfFilesAndDirectoriesIfGivenADirectory() throws Exception {
        String request = "GET /games HTTP/1.1";
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        String response = handler.handle(request);

        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" +
                "<!DOCTYPE html><html lang=\"en\"><body>" +
                "<a href=\"http://localhost:5000/games/1\">1</a>\r\n" +
                "<a href=\"http://localhost:5000/games/2\">2</a>\r\n" +
                "</body></html>", response);
    }

    public void testItDoesNotIncludeAOneUpLinkIfAtRootDirectory() throws Exception {
        String request = "GET /brians/ping-pong-equipment HTTP/1.1";
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        String response = handler.handle(request);

        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" +
                "<!DOCTYPE html><html lang=\"en\"><body>" +
                "<a href=\"http://localhost:5000/brians\">..</a>\r\n" +
                "<a href=\"http://localhost:5000/brians/ping-pong-equipment/lighting\">lighting</a>\r\n" +
                "<a href=\"http://localhost:5000/brians/ping-pong-equipment/nets\">nets</a>\r\n" +
                "<a href=\"http://localhost:5000/brians/ping-pong-equipment/paddles\">paddles</a>\r\n" +
                "</body></html>", response);
    }
}
