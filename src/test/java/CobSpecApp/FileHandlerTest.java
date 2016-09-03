package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.util.HashMap;

public class FileHandlerTest extends TestCase {

    public void test_it_handles_a_simple_request() throws Exception {
        Request request = new Request("GET", "/");
        String[] args = new String[3];
        args[0] = "-d";
        args[1] = "public";
        args[2] = "-ai";
        Setup settings = new Setup(args);
        Handler handler = new FileHandler(settings);

        Response response = handler.handle(request);

        assertEquals(wrapHtml("<h1>Hello World</h1>"), new String(response.getBody(), "UTF-8"));
        assertEquals("71", response.getHeader("Content-Length"));
        assertEquals("text/html", response.getHeader("Content-Type"));
    }

    public void test_it_returns_an_index_if_not_at_root() throws Exception {
        Request request = new Request("GET", "/brians");
        String[] args = new String[3];
        args[0] = "-d";
        args[1] = "public";
        args[2] = "-ai";
        Setup settings = new Setup(args);
        Handler handler = new FileHandler(settings);

        Response response = handler.handle(request);

        assertEquals(wrapHtml("<h1>Brian's cool website</h1>"), new String(response.getBody(), "UTF-8"));
        assertEquals("80", response.getHeader("Content-Length"));
        assertEquals("text/html", response.getHeader("Content-Type"));
    }

    public void test_it_reads_a_directory_structure_if_no_index_is_present() throws Exception {
        Request request = new Request("GET", "/");
        Handler handler = new FileHandler(new Setup());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("/brians", "brians") +
                createLink("/games", "games") +
                createLink("/geoffs-sweet-site", "geoffs-sweet-site") +
                createLink("/images", "images") +
                createLink("/index.html", "index.html");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
        assertEquals("text/html", response.getHeader("Content-Type"));
    }


    public void test_it_handles_a_basic_request() throws Exception {
        Request request = new Request("GET", "/brians/index.html");
        Handler handler = new FileHandler(new Setup());

        Response response = handler.handle(request);

        assertEquals(wrapHtml("<h1>Brian's cool website</h1>"), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_handles_indexes_as_slashes() throws Exception {
        Request request = new Request("GET", "/brians");
        Handler handler = new FileHandler(new Setup());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("/", "..") +
                createLink("/brians/index.html", "index.html") +
                createLink("/brians/ping-pong-equipment", "ping-pong-equipment");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_includes_a_link_to_navigate_up_the_chain() throws Exception {
        Request request = new Request("GET", "/brians/ping-pong-equipment/lighting");
        Handler handler = new FileHandler(new Setup());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("/brians/ping-pong-equipment", "..") +
                createLink("/brians/ping-pong-equipment/lighting/index.html", "index.html");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_returns_a_listing_of_files_if_given_a_directory() throws Exception {
        Request request = new Request("GET", "/games");
        Handler handler = new FileHandler(new Setup());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("/", "..") +
                createLink("/games/1", "1") +
                createLink("/games/2", "2");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_does_not_include_a_link_if_at_root_directory() throws Exception {
        Request request = new Request("GET", "/brians/ping-pong-equipment");
        Handler handler = new FileHandler(new Setup());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("/brians", "..") +
                createLink("/brians/ping-pong-equipment/lighting", "lighting") +
                createLink("/brians/ping-pong-equipment/nets", "nets") +
                createLink("/brians/ping-pong-equipment/paddles", "paddles");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_up_links_work_one_level_down() throws Exception {
        Request request = new Request("GET", "/brians");
        Handler handler = new FileHandler(new Setup());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("/", "..") +
                createLink("/brians/index.html", "index.html") +
                createLink("/brians/ping-pong-equipment", "ping-pong-equipment");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_returns_an_image_with_a_content_length_and_content_type_for_jpg() throws Exception {
        Request request = new Request("GET", "/images/hong-kong.jpg");
        Handler handler = new FileHandler(new Setup());

        Response response = handler.handle(request);

        assertEquals("2044854", response.getHeader("Content-Length"));
        assertEquals("image/jpeg", response.getHeader("Content-Type"));
        assertEquals(2044854, response.getBody().length);
    }

    public void test_it_returns_a_giffy_with_a_content_length_and_content_type() throws Exception {
        Request request = new Request("GET", "/geoffs-sweet-site/samurai-champloo/board.gif");
        Handler handler = new FileHandler(new Setup());

        Response response = handler.handle(request);

        assertEquals("898055", response.getHeader("Content-Length"));
        assertEquals("image/gif", response.getHeader("Content-Type"));
        assertEquals(898055, response.getBody().length);
    }

    public void test_it_can_handle_different_routes() throws Exception {
        Request request = new Request("GET", "/");
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = "src";
        Handler handler = new FileHandler(new Setup(args));

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
