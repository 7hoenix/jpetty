package Server.Parsers;

import HTTPServer.Parsers.RequestLineParser;
import junit.framework.TestCase;

import java.util.Map;

public class RequestLineParserTest extends TestCase {
    public void test_it_can_find_the_path_and_the_action() throws Exception {
        byte[] rawRequest = "PATCH /patch-request.txt HTTP/1.1\r\nAccept: Cake\r\nContent-Type: Pie\r\n\r\n".getBytes();

        Map<String, String> line = new RequestLineParser().parse(rawRequest);

        assertEquals("PATCH", line.get("action"));
        assertEquals("/patch-request.txt", line.get("path"));
    }

    public void test_it_parses_a_complicated_query_correctly_to_return_the_path() throws Exception {
        byte[] rawRequest = "GET /pie?it=good&betterThan=cake HTTP/1.1\r\nAccept: Cake\r\nContent-Type: Pie\r\n\r\n".getBytes();

        Map<String, String> line = new RequestLineParser().parse(rawRequest);

        assertEquals("/pie", line.get("path"));
    }
}
