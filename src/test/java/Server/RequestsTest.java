package Server;

import HTTPServer.Requests;
import junit.framework.TestCase;

public class RequestsTest extends TestCase {
    public void test_it_parses_a_byte_array_and_returns_the_header() throws Exception {
        byte[] input = "GET / HTTP/1.1\r\n\r\nhi mom\r\n".getBytes();

        String header = Requests.findLine(input);

        assertEquals("GET / HTTP/1.1\r\n", header);
    }

//    public void test_it_parses_an_array_of_strings_to_return_the_total_header() throws Exception {
//        byte[] input = "GET / HTTP/1.1\r\nContent-Type: text/html\r\nContent-Length: 13\r\n\r\nhi mom".getBytes();
//
//        String header = Requests.findHeader(input);
//
//        assertEquals("GET / HTTP/1.1\r\nContent-Type: text/html\r\nContent-Length: 13\r\n", header);
//    }

//    public void test_it_parses_an_array_of_strings_and_returns_the_body() throws Exception {
//        String[] request = new String[3];
//        request[0] = "GET / HTTP/1.1";
//        request[1] = "";
//        request[2] = "hi mom";
//
//        String header = Requests.findBody(request);
//
//        assertEquals("hi mom\r\n", header);
//    }

//    public void test_it_returns_an_empty_string_if_no_body_is_found() throws Exception {
//        String[] request = new String[2];
//        request[0] = "GET / HTTP/1.1";
//        request[1] = "";
//
//        String header = Requests.findBody(request);
//
//        assertEquals("", header);
//    }
}
