package Server;

import HTTPServer.RequestHeaderParser;
import junit.framework.TestCase;

import java.util.Map;

public class RequestHeaderParserTest extends TestCase {
    public void test_it_can_parse_headers_into_a_map() throws Exception {
        byte[] fullRequest = "PATCH /patch-request.txt HTTP/1.1\r\nAccept: Cake\r\nContent-Type: Pie\r\n\r\n".getBytes();

        Map<String, String> headers = RequestHeaderParser.parseHeaders(fullRequest);

        assertEquals("Cake", headers.get("Accept"));
        assertEquals("Pie", headers.get("Content-Type"));
    }

    public void test_it_can_find_the_path_and_the_action() throws Exception {
        byte[] fullRequest = "PATCH /patch-request.txt HTTP/1.1\r\nAccept: Cake\r\nContent-Type: Pie\r\n\r\n".getBytes();

        Map<String, String> parsedLine = RequestHeaderParser.parseLine(fullRequest);

        assertEquals("PATCH", parsedLine.get("action"));
        assertEquals("/patch-request.txt", parsedLine.get("path"));
    }

    public void test_it_parses_a_complicated_query_correctly_to_return_the_path() throws Exception {
        byte[] fullRequest = "GET /pie?it=good&betterThan=cake HTTP/1.1\r\nAccept: Cake\r\nContent-Type: Pie\r\n\r\n".getBytes();

        Map<String, String> parsedLine = RequestHeaderParser.parseLine(fullRequest);

        assertEquals("/pie", parsedLine.get("path"));
    }
}
