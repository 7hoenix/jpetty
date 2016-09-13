package Server;

import HTTPServer.ParserHelper;
import junit.framework.TestCase;

public class ParserHelperTest extends TestCase {
    public void test_it_can_parse_a_byte_array_based_on_a_query_and_a_length() throws Exception {
        byte[] input = "should be first space only".getBytes();

        assertEquals("should", ParserHelper.parseQuery(input, " "));
    }

    public void test_it_works_with_CRLFs() throws Exception {
        byte[] input = "GET / HTTP/1.1\r\nyou will never see me\r\n\r\n".getBytes();

        assertEquals("GET / HTTP/1.1", ParserHelper.parseQuery(input, "\r\n"));
    }

    public void test_it_works_for_double_CRLFs() throws Exception {
        byte[] input = "GET / HTTP/1.1\r\n\r\nyou will never see me\r\n".getBytes();

        assertEquals("GET / HTTP/1.1", ParserHelper.parseQuery(input, "\r\n\r\n"));
    }

    public void test_it_returns_the_entire_result_if_no_query_match_is_found() throws Exception {
        byte[] input = "GET / HTTP/1.1\r\n\r\nyou will see me\r\n".getBytes();

        assertEquals("GET / HTTP/1.1\r\n\r\nyou will see me", ParserHelper.parseQuery(input, "arbitrary"));
    }

    public void test_it_returns_an_array_of_strings_based_on_the_split_parameter() throws Exception {
        String header = "GET / HTTP/1.1\r\nAccept: ok\r\nContent-Length: 4";

        assertEquals("Accept: ok", ParserHelper.splitOnParameter(header, "\r\n")[1]);
    }

    public void test_it_returns_a_blank_array_if_not_valid_request() throws Exception {
        String[] result = ParserHelper.splitOnParameter(null, "\r\n\r\n");

        assertEquals(0, result.length);
    }

    public void test_it_returns_a_blank_array_if_not_valid_query() throws Exception {
        String header = "GET / HTTP/1.1\r\nAccept: ok\r\nContent-Length: 4";

        String[] result = ParserHelper.splitOnParameter(header, null);

        assertEquals(0, result.length);
    }
}
