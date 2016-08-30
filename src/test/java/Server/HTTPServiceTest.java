package Server;

import HTTPServer.HTTPService;
import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.io.*;

public class HTTPServiceTest extends TestCase {
    public void testItHandlesASimpleRequest() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET / HTTP/1.1\r\nContent-Type: text/html\r\n\r\n".getBytes());
        String[] args = new String[3];
        args[0] = "-d";
        args[1] = "public";
        args[2] = "-ai";
        Setup settings = new Setup(args);
        HTTPService service = new HTTPService(settings);

        Response response = service.processInput(inputStream);

        String basicResponse = basicResponse("Content-Type: text/html\r\nConnection: close\r\n" +
                "Content-Length: 71\r\n\r\n", wrapHtml("<h1>Hello World</h1>"));
        assertEquals(basicResponse, new String(response.getFull(), "UTF-8"));
    }

    public void testItReturnsAnIndexIfNotAtRoot() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /brians HTTP/1.1\r\n\r\n".getBytes());
        String[] args = new String[3];
        args[0] = "-d";
        args[1] = "public";
        args[2] = "-ai";
        Setup settings = new Setup(args);
        HTTPService service = new HTTPService(settings);

        Response response = service.processInput(request);

        String basicResponse = basicResponse("Content-Type: text/html\r\n" +
                "Connection: close\r\nContent-Length: 80\r\n\r\n", wrapHtml("<h1>Brian's cool website</h1>"));
        assertEquals(basicResponse, new String(response.getFull(), "UTF-8"));
    }

    public void testItReadsADirectoryStructureIfNoIndexPresent() throws Exception {
        InputStream request = new ByteArrayInputStream("GET / HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        String innerHtml = "" +
                createLink("/brians", "brians") +
                createLink("/games", "games") +
                createLink("/geoffs-sweet-site", "geoffs-sweet-site") +
                createLink("/images", "images") +
                createLink("/index.html", "index.html");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response.getFull(), "UTF-8"));
    }

    public void testItHandlesABasicRequest() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /brians/index.html HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        String basicResponse = basicResponse("Content-Type: text/html\r\nConnection: close\r\n" +
                "Content-Length: 80\r\n\r\n", wrapHtml("<h1>Brian's cool website</h1>"));
        assertEquals(basicResponse, new String(response.getFull(), "UTF-8"));
    }

    public void testItHandlesNotValid() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /cake HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        assertEquals("HTTP/1.1 404 NOT FOUND\r\n\r\n", new String(response.getFull(), "UTF-8"));
    }

    public void testItHandlesIndexesAsSlashes() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /brians HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        String innerHtml = "" +
                createLink("/", "..") +
                createLink("/brians/index.html", "index.html") +
                createLink("/brians/ping-pong-equipment", "ping-pong-equipment");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response.getFull(), "UTF-8"));
    }

    public void testItIncludesALinkToNavigateUpTheChain() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /brians/ping-pong-equipment/lighting HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        String innerHtml = "" +
                createLink("/brians/ping-pong-equipment", "..") +
                createLink("/brians/ping-pong-equipment/lighting/index.html", "index.html");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response.getFull(), "UTF-8"));
    }

    public void testItReturnsAListingOfFilesAndDirectoriesIfGivenADirectory() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /games HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        String innerHtml = "" +
                createLink("/", "..") +
                createLink("/games/1", "1") +
                createLink("/games/2", "2");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response.getFull(), "UTF-8"));
    }

    public void testItDoesNotIncludeAOneUpLinkIfAtRootDirectory() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /brians/ping-pong-equipment HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        String innerHtml = "" +
                createLink("/brians", "..") +
                createLink("/brians/ping-pong-equipment/lighting", "lighting") +
                createLink("/brians/ping-pong-equipment/nets", "nets") +
                createLink("/brians/ping-pong-equipment/paddles", "paddles");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response.getFull(), "UTF-8"));
    }

    public void testUpLinksWorkOneLevelDown() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /brians HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        String innerHtml = "" +
                createLink("/", "..") +
                createLink("/brians/index.html", "index.html") +
                createLink("/brians/ping-pong-equipment", "ping-pong-equipment");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response.getFull(), "UTF-8"));
    }

    public void testItReturnsAContentTypeOfImageJpegForAJpeg() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /images/hong-kong.jpg HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        String responseHeader = new String(response.getHeader(), "UTF-8").split("\r\n\r\n")[0];
        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: image/jpeg\r\nConnection: close\r\nContent-Length: 2044854\r\n", responseHeader);
    }

    public void testItIncludesTheImageInTheBody() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /images/hong-kong.jpg HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        assertEquals(2044854, response.getBody().length);
    }

    public void testItCanReturnAGiphy() throws Exception {
        InputStream request = new ByteArrayInputStream("GET /geoffs-sweet-site/samurai-champloo/board.gif HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        assertEquals(898055, response.getBody().length);
        String responseHeader = new String(response.getHeader(), "UTF-8").split("\r\n\r\n")[0];
        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: image/gif\r\nConnection: close\r\nContent-Length: 898055\r\n", responseHeader);
    }

    public void testItCanHandleDifferentRoutes() throws Exception {
        InputStream request = new ByteArrayInputStream("GET / HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("src");

        Response response = service.processInput(request);

        String innerHtml = "" +
                createLink("/main", "main") +
                createLink("/test", "test");
        String basicResponse = basicResponse("Content-Type: text/html\r\n\r\n", wrapHtml(innerHtml));
        assertEquals(basicResponse, new String(response.getFull(), "UTF-8"));
    }

    public void testItCanHandleAHeadRequest() throws Exception {
        InputStream request = new ByteArrayInputStream("HEAD /geoffs-sweet-site/samurai-champloo/board.gif HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        String responseHeader = new String(response.getHeader(), "UTF-8");
        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: image/gif\r\nConnection: close\r\nContent-Length: 898055\r\n", responseHeader);
    }

    public void testItCanHandleAHeadRequestToAdirectory() throws Exception {
        InputStream request = new ByteArrayInputStream("HEAD / HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("otherPublic");

        Response response = service.processInput(request);

        String responseHeader = new String(response.getHeader(), "UTF-8");
        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n", responseHeader);
    }

    public void testItCanHandleAHeadRequestToARouteThatIsNotPresent() throws Exception {
        InputStream request = new ByteArrayInputStream("HEAD /foo HTTP/1.1\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        assertEquals("HTTP/1.1 404 NOT FOUND\r\n\r\n", new String(response.getFull(), "UTF-8"));
    }

    public void testItCanHandleABlankRequest() throws Exception {
        InputStream request = new ByteArrayInputStream("\r\n\r\n".getBytes());
        HTTPService service = new HTTPService("public");

        Response response = service.processInput(request);

        assertEquals("HTTP/1.1 400 BAD REQUEST\r\n\r\n", new String(response.getFull(), "UTF-8"));
    }

    private String basicResponse(String headers, String html) {
        return "HTTP/1.1 200 OK\r\n" + headers + html;
    }

    private String wrapHtml(String innerText) {
        return "<!DOCTYPE html><html lang=\"en\"><body>" + innerText + "</body></html>";
    }

    private String createLink(String path, String resource) {
        return "<a href=\"" + path + "\">" + resource + "</a>\r\n";
    }
}
