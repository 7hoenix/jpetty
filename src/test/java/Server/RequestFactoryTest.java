package Server;

import HTTPServer.RequestFactory;
import HTTPServer.Request;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RequestFactoryTest extends TestCase {
    public void test_it_handles_just_a_header_in_the_request() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET / HTTP/1.1\r\n\r\n".getBytes());
        RequestFactory factory = new RequestFactory();

        Request request = factory.create(inputStream);

        assertEquals("", request.getBody());
        assertEquals("GET / HTTP/1.1", request.getHeader());
        assertEquals("GET / HTTP/1.1\r\n\r\n", new String(request.getFullRequest(), "UTF-8"));
    }

    public void test_it_parses_a_body_and_a_header() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET /parameters?a=1&b=2 HTTP/1.1\r\n\r\nhi mom".getBytes());
        RequestFactory parser = new RequestFactory();

        Request request = parser.create(inputStream);

        assertEquals("GET /parameters?a=1&b=2 HTTP/1.1", request.getHeader());
        assertEquals("hi mom", request.getBody());
        assertEquals("GET /parameters?a=1&b=2 HTTP/1.1\r\n\r\nhi mom", new String(request.getFullRequest(), "UTF-8"));
    }

    public void test_it_can_handle_a_blank_request() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        RequestFactory parser = new RequestFactory();

        Request request = parser.create(inputStream);

        assertEquals("", request.getHeader());
        assertEquals("", request.getBody());
        assertEquals("", new String(request.getFullRequest(), "UTF-8"));
    }

}
