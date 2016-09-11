package Server.Handlers;

import HTTPServer.*;
import HTTPServer.Handlers.FileSystemHandler;
import HTTPServer.Handlers.Handler;
import junit.framework.TestCase;

public class FileSystemHandlerTest extends TestCase {
    public void test_it_returns_a_listing_of_the_methods_that_will_work_on_a_resource() throws Exception {
        Request request = new Request("/method_options", "OPTIONS");
        Router router = new Router()
                .add("/method_options", new String[] {"GET", "HEAD", "POST", "PUT"}, new MockHandler(200));
        Handler handler = new FileSystemHandler(new Settings(new String[0]), router);

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("HEAD,POST,GET,PUT,OPTIONS", response.getHeader("Allow"));
    }

    public void test_it_returns_options_by_default() throws Exception {
        Request request = new Request("/method_options2", "OPTIONS");
        Router router = new Router().add("/method_options2", "PUT", new MockHandler(200));
        Handler handler = new FileSystemHandler(new Settings(new String[0]), router);

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("PUT,OPTIONS", response.getHeader("Allow"));
    }

    public void test_it_returns_the_contents_of_a_file() throws Exception {
        Request request = new Request("/fakeDirectory/thingy.html", "GET");
        Handler handler = new FileSystemHandler(new Settings(new String[] {"-d", "otherPublic"}), new Router());

        Response response = handler.handle(request);

        String expectedBody = "<!DOCTYPE html><html lang=\"en\"><body><h1>Thingy has a point!</h1></body></html>\n";
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedBody, new String(response.getBody(), "UTF-8"));
    }

    public void test_it_reads_a_directory_structure_if_no_index_is_present() throws Exception {
        Request request = new Request("/", "GET");
        Handler handler = new FileSystemHandler(new Settings(new String[] {"-d", "otherPublic"}), new Router());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("/fakeDirectory", "fakeDirectory") +
                createLink("/thing.txt", "thing.txt");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
        assertEquals("text/html", response.getHeader("Content-Type"));
    }

    public void test_it_handles_a_simple_request() throws Exception {
        Request request = new Request("/", "GET");
        Handler handler = new FileSystemHandler(new Settings(new String[] {"-d", "public", "-ai"}), new Router());

        Response response = handler.handle(request);

        assertEquals(wrapHtml("<h1>Hello World</h1>"), new String(response.getBody(), "UTF-8"));
        assertEquals("71", response.getHeader("Content-Length"));
        assertEquals("text/html", response.getHeader("Content-Type"));
    }

    public void test_it_returns_an_index_if_not_at_root() throws Exception {
        Request request = new Request("/brians", "GET");
        Handler handler = new FileSystemHandler(new Settings(new String[] {"-d", "public", "-ai"}), new Router());

        Response response = handler.handle(request);

        assertEquals(wrapHtml("<h1>Brian's cool website</h1>"), new String(response.getBody(), "UTF-8"));
        assertEquals("80", response.getHeader("Content-Length"));
        assertEquals("text/html", response.getHeader("Content-Type"));
    }

    public void test_it_handles_a_basic_request() throws Exception {
        Request request = new Request("/brians/index.html", "GET");
        Handler handler = new FileSystemHandler(new Settings(), new Router());

        Response response = handler.handle(request);

        assertEquals(wrapHtml("<h1>Brian's cool website</h1>"), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_handles_indexes_as_slashes() throws Exception {
        Request request = new Request("/brians", "GET");
        Handler handler = new FileSystemHandler(new Settings(), new Router());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("..", "..") +
                createLink("/brians/index.html", "index.html") +
                createLink("/brians/ping-pong-equipment", "ping-pong-equipment");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_includes_a_link_to_navigate_up_the_chain() throws Exception {
        Request request = new Request("/brians/ping-pong-equipment/lighting", "GET");
        Handler handler = new FileSystemHandler(new Settings(), new Router());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("..", "..") +
                createLink("/brians/ping-pong-equipment/lighting/index.html", "index.html");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_returns_a_listing_of_files_if_given_a_directory() throws Exception {
        Request request = new Request("/games", "GET");
        Handler handler = new FileSystemHandler(new Settings(), new Router());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("..", "..") +
                createLink("/games/1", "1") +
                createLink("/games/2", "2");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_does_not_include_a_link_if_at_root_directory() throws Exception {
        Request request = new Request("/brians/ping-pong-equipment", "GET");
        Handler handler = new FileSystemHandler(new Settings(), new Router());

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
        Handler handler = new FileSystemHandler(new Settings(), new Router());

        Response response = handler.handle(request);

        String innerHtml = "" +
                createLink("..", "..") +
                createLink("/brians/index.html", "index.html") +
                createLink("/brians/ping-pong-equipment", "ping-pong-equipment");
        assertEquals(wrapHtml(innerHtml), new String(response.getBody(), "UTF-8"));
    }

    public void test_it_returns_an_image_with_a_content_length_and_content_type_for_jpg() throws Exception {
        Request request = new Request("/images/hong-kong.jpg", "GET");
        Handler handler = new FileSystemHandler(new Settings(), new Router());

        Response response = handler.handle(request);

        assertEquals("2044854", response.getHeader("Content-Length"));
        assertEquals("image/jpeg", response.getHeader("Content-Type"));
        assertEquals(2044854, response.getBody().length);
    }

    public void test_it_returns_a_giffy_with_a_content_length_and_content_type() throws Exception {
        Request request = new Request("/geoffs-sweet-site/samurai-champloo/board.gif", "GET");
        Handler handler = new FileSystemHandler(new Settings(), new Router());

        Response response = handler.handle(request);

        assertEquals("898055", response.getHeader("Content-Length"));
        assertEquals("image/gif", response.getHeader("Content-Type"));
        assertEquals(898055, response.getBody().length);
    }

    public void test_it_can_handle_different_routes() throws Exception {
        Request request = new Request("/", "GET");
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = "src";
        Handler handler = new FileSystemHandler(new Settings(args), new Router());

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
