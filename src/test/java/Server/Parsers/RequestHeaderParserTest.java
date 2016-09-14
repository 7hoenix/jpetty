package Server.Parsers;

import HTTPServer.Parsers.RequestHeaderParser;
import junit.framework.TestCase;

import java.util.Map;

public class RequestHeaderParserTest extends TestCase {
    public void test_it_can_parse_headers_into_a_map() throws Exception {
        byte[] rawRequest = "PATCH /patch-request.txt HTTP/1.1\r\nAccept: Cake\r\nContent-Type: Pie\r\n\r\n".getBytes();

        Map<String, String> headers = new RequestHeaderParser().parse(rawRequest);

        assertEquals("Cake", headers.get("Accept"));
        assertEquals("Pie", headers.get("Content-Type"));
    }
}
