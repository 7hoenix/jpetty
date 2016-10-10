package serverTest.staticFileHandlers;

import server.Handler;
import server.Request;
import server.Response;
import junit.framework.TestCase;
import server.staticFileHandlers.PartialContentHandler;

import java.io.File;

public class PartialContentTest extends TestCase {
    public void test_it_returns_a_partial_number_of_bytes_from_a_requested_file() throws Exception {
        Request request = new Request("/geoffs-sweet-site/test.txt", "GET").setHeader("Range", "bytes=0-4");
        Handler handler = new PartialContentHandler(new File("public"));

        Response response = handler.handle(request);

        assertEquals(206, response.getStatusCode());
        assertEquals("bytes 0-5/27", response.getHeader("Content-Range"));
        assertEquals("WHAT ", new String(response.getBody(), "UTF-8"));
    }

    public void test_it_returns_the_final_number_of_bytes_counting_from_the_end() throws Exception {
        Request request = new Request("/geoffs-sweet-site/test.txt", "GET").setHeader("Range", "bytes=-5");
        Handler handler = new PartialContentHandler(new File("public"));

        Response response = handler.handle(request);

        assertEquals(206, response.getStatusCode());
        assertEquals("N????", new String(response.getBody(), "UTF-8"));
        assertEquals("bytes 22-27/27", response.getHeader("Content-Range"));
    }

    public void test_it_returns_a_block_of_bytes_from_the_center_of_the_file() throws Exception {
        Request request = new Request("/geoffs-sweet-site/test.txt", "GET").setHeader("Range", "bytes=15-22");
        Handler handler = new PartialContentHandler(new File("public"));

        Response response = handler.handle(request);

        assertEquals(206, response.getStatusCode());
        assertEquals("bytes 15-23/27", response.getHeader("Content-Range"));
        assertEquals("O HAPPEN", new String(response.getBody(), "UTF-8"));
    }

    public void test_it_returns_a_block_of_bytes_from_start_to_end_of_file_if_no_last_value_given() throws Exception {
        Request request = new Request("/geoffs-sweet-site/test.txt", "GET").setHeader("Range", "bytes=15-");
        Handler handler = new PartialContentHandler(new File("public"));

        Response response = handler.handle(request);

        assertEquals(206, response.getStatusCode());
        assertEquals("O HAPPEN????", new String(response.getBody(), "UTF-8"));
        assertEquals("bytes 15-27/27", response.getHeader("Content-Range"));
    }
}
