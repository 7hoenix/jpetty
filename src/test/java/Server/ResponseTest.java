package Server;

import HTTPServer.Response;
import junit.framework.TestCase;

public class ResponseTest extends TestCase {
    public void test_it_knows_its_header() throws Exception {
        byte[] header = "HTTP/1.1 200 OK\r\n".getBytes();
        byte[] body = "HI there.".getBytes();

        Response response = new Response(header, body);

        assertEquals("HTTP/1.1 200 OK\r\n", new String(response.getHeader(), "UTF-8"));
        assertEquals("HI there.", new String(response.getBody(), "UTF-8"));
        assertEquals("HTTP/1.1 200 OK\r\n\r\nHI there.", new String(response.getFull(), "UTF-8"));
    }

    public void test_it_returns_just_the_headers_if_no_body_is_found() throws Exception {
        byte[] header = "HTTP/1.1 200 OK\r\n".getBytes();

        Response response = new Response(header);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", new String(response.getFull(), "UTF-8"));

    }
}
