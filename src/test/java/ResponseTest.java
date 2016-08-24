import HTTPServer.Response;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 8/24/16.
 */
public class ResponseTest extends TestCase {
    public void test_it_knows_its_header() throws Exception {
        Response response = new Response();

        response.setHeader("HTTP/1.1 200 OK\r\n".getBytes());
        response.setBody("HI there.".getBytes());

        assertEquals("HTTP/1.1 200 OK\r\n", new String(response.getHeader(), "UTF-8"));
        assertEquals("HI there.", new String(response.getBody(), "UTF-8"));
        assertEquals("HTTP/1.1 200 OK\r\n\r\nHI there.", new String(response.getFull(), "UTF-8"));
    }

    public void test_it_returns_just_the_headers_if_no_body_is_found() throws Exception {
        Response response = new Response();

        response.setHeader("HTTP/1.1 200 OK\r\n".getBytes());

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", new String(response.getFull(), "UTF-8"));

    }
}
