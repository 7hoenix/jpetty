package serverTest.parsers;

import server.Request;
import junit.framework.TestCase;
import server.parsers.RequestParser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RequestParserTest extends TestCase {
    public void test_it_parses_an_input_stream_and_returns_a_new_request_object() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET / HTTP/1.1\r\n\r\n".getBytes());
        RequestParser parser = new RequestParser();

        Request request = parser.parse(inputStream);

        assertEquals("GET", request.getAction());
        assertEquals("/", request.getPath());
    }

    public void test_it_parses_any_headers_and_puts_them_in_the_request_object() throws Exception {
        InputStream inputStream = new ByteArrayInputStream(("GET / HTTP/1.1\r\nContent-Type: text/html\r\n" +
                "Content-Length: 2\r\nKeep: alive\r\n\r\n").getBytes());
        RequestParser parser = new RequestParser();

        Request request = parser.parse(inputStream);

        assertEquals("2", request.getHeader("Content-Length"));
        assertEquals("text/html", request.getHeader("Content-Type"));
    }

    public void test_it_parses_params_and_puts_them_in_the_request_object() throws Exception {
        InputStream inputStream = new ByteArrayInputStream(("GET /?a=1&b=2 HTTP/1.1\r\n\r\n").getBytes());
        RequestParser parser = new RequestParser();

        Request request = parser.parse(inputStream);

        assertEquals("1", request.getParam("a"));
        assertEquals("2", request.getParam("b"));
    }
}
