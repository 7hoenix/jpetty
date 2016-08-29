package Server;

import HTTPServer.Request;
import junit.framework.TestCase;

import java.util.HashMap;

public class RequestTest extends TestCase {
    public void test_it_knows_its_headers() throws Exception {
        String header = new String("POST / HTTP/1.1\r\nContent-Length: 13\r\nContent-Type: application/x-www-form-urlencoded\r\n");
        String body = new String("hi=mom");

        Request request = new Request(new HashMap(), header, body);

        assertEquals("POST / HTTP/1.1\r\nContent-Length: 13\r\nContent-Type: application/x-www-form-urlencoded\r\n", request.getHeader());
        assertEquals("hi=mom", request.getBody());
    }
}
