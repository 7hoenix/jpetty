package Server.StaticFileHandlers;

import HTTPServer.BasicHandler;
import HTTPServer.Handler;
import HTTPServer.Middleware;
import HTTPServer.Middlewares.StaticFileHandler;
import HTTPServer.Request;
import HTTPServer.Response;
import junit.framework.TestCase;

import java.io.File;

public class DirectoryHandlerTest extends TestCase {

    public void test_it_compiles() throws Exception {
        assertEquals(1, 1);
    }

    public void test_it_returns_the_contents_of_a_file() throws Exception {
        Request request = new Request("/fakeDirectory/thingy.html", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("otherPublic"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        String expectedBody = "<!DOCTYPE html><html lang=\"en\"><body><h1>Thingy has a point!</h1></body></html>\n";
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedBody, new String(response.getBody(), "UTF-8"));
    }

    public void test_it_reads_a_directory_structure_if_no_index_is_present() throws Exception {
        Request request = new Request("/", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("otherPublic"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("/fakeDirectory", "fakeDirectory") +
                createLink("/patch-content.txt", "patch-content.txt") +
                createLink("/thing.txt", "thing.txt");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
        assertEquals("text/html", response.getHeader("Content-Type"));
    }

    public void test_it_handles_a_simple_request() throws Exception {
        Request request = new Request("/", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("public"))
                .setAutoIndex(true);
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        assertEquals(wrapHtml("<h1>Hello World</h1>"), new String(response.getBody(), "UTF-8"));
        assertEquals("71", response.getHeader("Content-Length"));
        assertEquals("text/html", response.getHeader("Content-Type"));
    }

    public void test_it_returns_an_index_if_not_at_root() throws Exception {
        Request request = new Request("/brians", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("public"))
                .setAutoIndex(true);
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        assertEquals(wrapHtml("<h1>Brian's cool website</h1>"), new String(response.getBody(), "UTF-8"));
        assertEquals("80", response.getHeader("Content-Length"));
        assertEquals("text/html", response.getHeader("Content-Type"));
    }

    public void test_it_handles_a_basic_request() throws Exception {
        Request request = new Request("/brians/index.html", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("public"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        assertEquals(wrapHtml("<h1>Brian's cool website</h1>"), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_handles_indexes_as_slashes() throws Exception {
        Request request = new Request("/brians", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("public"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("..", "..") +
                createLink("/brians/index.html", "index.html") +
                createLink("/brians/ping-pong-equipment", "ping-pong-equipment");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_includes_a_link_to_navigate_up_the_chain() throws Exception {
        Request request = new Request("/brians/ping-pong-equipment/lighting", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("public"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("..", "..") +
                createLink("/brians/ping-pong-equipment/lighting/index.html", "index.html");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_returns_a_listing_of_files_if_given_a_directory() throws Exception {
        Request request = new Request("/games", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("public"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("..", "..") +
                createLink("/games/1", "1") +
                createLink("/games/2", "2");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_does_not_include_a_link_if_at_root_directory() throws Exception {
        Request request = new Request("/brians/ping-pong-equipment", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("public"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("..", "..") +
                createLink("/brians/ping-pong-equipment/lighting", "lighting") +
                createLink("/brians/ping-pong-equipment/nets", "nets") +
                createLink("/brians/ping-pong-equipment/paddles", "paddles");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_up_links_work_one_level_down() throws Exception {
        Request request = new Request("/brians", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("public"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("..", "..") +
                createLink("/brians/index.html", "index.html") +
                createLink("/brians/ping-pong-equipment", "ping-pong-equipment");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_returns_an_image_with_a_content_length_and_content_type_for_jpg() throws Exception {
        Request request = new Request("/images/hong-kong.jpg", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("public"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        assertEquals("2044854", response.getHeader("Content-Length"));
        assertEquals("image/jpeg", response.getHeader("Content-Type"));
        assertEquals(2044854, response.getBody().length);
    }

    public void test_it_returns_a_giffy_with_a_content_length_and_content_type() throws Exception {
        Request request = new Request("/geoffs-sweet-site/samurai-champloo/board.gif", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("public"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        assertEquals("898055", response.getHeader("Content-Length"));
        assertEquals("image/gif", response.getHeader("Content-Type"));
        assertEquals(898055, response.getBody().length);
    }

    public void test_it_can_handle_different_routes() throws Exception {
        Request request = new Request("/", "GET");
        Middleware staticFileMiddleware = new StaticFileHandler()
                .setPublicDirectory(new File("src"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("/main", "main") +
                createLink("/test", "test");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    private String wrapHtml(String innerText) {
        return "<!DOCTYPE html><html lang=\"en\"><body>" + innerText + "</body></html>";
    }

    private String createLink(String path, String resource) {
        return "<a href=\"" + path + "\">" + resource + "</a>\r\n";
    }
}
