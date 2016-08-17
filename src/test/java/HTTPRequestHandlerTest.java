import HTTPServer.HTTPRequestHandler;
import junit.framework.TestCase;

import java.io.*;

/**
 * Created by jphoenix on 8/4/16.
 */
public class HTTPRequestHandlerTest extends TestCase {
    public void testItHandlesASimpleRequest() throws Exception {
        InputStream request = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        byte[] response = handler.handle(request);

        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml("<h1>Hello World</h1>"));
        assertEquals(basicResponse, new String(response, "UTF-8"));
    }

    public void testItHandlesABasicRequest() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /brians/index.html HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        byte[] response = handler.handle(request);

        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml("<h1>Brian's cool website</h1>"));
        assertEquals(basicResponse, new String(response, "UTF-8"));
    }

    public void testItHandlesNotValid() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /cake HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        byte[] response = handler.handle(request);

        assertEquals("HTTP/1.1 404 NOT FOUND\r\n\r\n", new String(response, "UTF-8"));
    }

    public void testItHandlesIndexesAsSlashes() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /brians HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        byte[] response = handler.handle(request);

        String innerHtml = "" +
                createLink("", "..") +
                createLink("/brians/index.html", "index.html") +
                createLink("/brians/ping-pong-equipment", "ping-pong-equipment");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response, "UTF-8"));
    }

    public void testItIncludesALinkToNavigateUpTheChain() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /brians/ping-pong-equipment/lighting HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        byte[] response = handler.handle(request);

        String innerHtml = "" +
                createLink("/brians/ping-pong-equipment", "..") +
                createLink("/brians/ping-pong-equipment/lighting/index.html", "index.html");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response, "UTF-8"));
    }

    public void testItReturnsAListingOfFilesAndDirectoriesIfGivenADirectory() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /games HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        byte[] response = handler.handle(request);

        String innerHtml = "" +
                createLink("", "..") +
                createLink("/games/1", "1") +
                createLink("/games/2", "2");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response, "UTF-8"));
    }

    public void testItDoesNotIncludeAOneUpLinkIfAtRootDirectory() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /brians/ping-pong-equipment HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        byte[] response = handler.handle(request);

        String innerHtml = "" +
                createLink("/brians", "..") +
                createLink("/brians/ping-pong-equipment/lighting", "lighting") +
                createLink("/brians/ping-pong-equipment/nets", "nets") +
                createLink("/brians/ping-pong-equipment/paddles", "paddles");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response, "UTF-8"));
    }

    public void testItReturnsAContentTypeOfImageJpegForAJpeg() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /images/hong-kong.jpg HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        byte[] response = handler.handle(request);

        String responseHeader = new String(response, "UTF-8").split("\r\n\r\n")[0];
        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: image/jpeg\r\nConnection: close\r\nContent-Length: 2044854", responseHeader);
    }

    public void testItIncludesTheImageInTheBody() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /images/hong-kong.jpg HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        byte[] response = handler.handle(request);

        assertEquals(2044943, response.length);
    }

    public void testItCanReturnAGiphy() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /geoffs-sweet-site/samurai-champloo/board.gif HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("public");

        byte[] response = handler.handle(request);

        assertEquals(898142, response.length);
        String responseHeader = new String(response, "UTF-8").split("\r\n\r\n")[0];
        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: image/gif\r\nConnection: close\r\nContent-Length: 898055", responseHeader);
    }

    public void testItCanHandleDifferentRoutes() throws Exception {
        InputStream request = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());
        HTTPRequestHandler handler = new HTTPRequestHandler("src");

        byte[] response = handler.handle(request);

        String innerHtml = "" +
                createLink("/main", "main") +
                createLink("/test", "test");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response, "UTF-8"));
    }

    private String basicResponse(String headers, String html) {
        return "HTTP/1.1 200 OK\r\n" + headers + html;
    }

    private String wrapHtml(String innerText) {
        return "<!DOCTYPE html><html lang=\"en\"><body>" + innerText + "</body></html>";
    }

    private String createLink(String path, String resource) {
        return "<a href=\"http://localhost:5000" + path + "\">" + resource + "</a>\r\n";
    }
}
