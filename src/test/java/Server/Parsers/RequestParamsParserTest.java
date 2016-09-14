package Server.Parsers;

import HTTPServer.Parsers.RequestParamsParser;
import junit.framework.TestCase;

import java.util.Map;

public class RequestParamsParserTest extends TestCase {
    public void test_it_parses_params_out_of_the_query_string() throws Exception {
        byte[] rawRequest = "GET /pie?is=good&betterThan=cake HTTP/1.1\r\nAccept: Cake\r\nContent-Type: Pie\r\n\r\nShould not matter\r\n".getBytes();

        Map<String, String> params = new RequestParamsParser().parse(rawRequest);

        assertEquals("good", params.get("is"));
        assertEquals("cake", params.get("betterThan"));
    }

    public void test_it_does_not_parse_params_out_of_query_string_if_put_or_post_request() throws Exception {
        byte[] rawRequest = "PUT /pie?is=good&betterThan=cake HTTP/1.1\r\nAccept: Cake\r\nContent-Type: Pie\r\n\r\nShould not matter\r\n".getBytes();

        Map<String, String> params = new RequestParamsParser().parse(rawRequest);

        assertEquals(0, params.size());
    }

    public void test_it_parses_a_form_if_post_or_put() throws Exception {
        byte[] rawRequest = "PUT /pie?is=good&betterThan=cake HTTP/1.1\r\n\r\na=fat%20cat&b=skinny%20pig".getBytes();

        Map<String, String> params = new RequestParamsParser().parse(rawRequest);

        assertEquals("fat cat", params.get("a"));
        assertEquals("skinny pig", params.get("b"));
        assertNotSame("good", params.get("is"));
        assertNotSame("cake", params.get("betterThan"));
    }

    public void test_it_does_not_assign_any_params_if_none_are_found_in_the_query_string() throws Exception {
        byte[] rawRequest = "GET /pie HTTP/1.1\r\n\r\nNOT SEEN".getBytes();

        Map<String, String> params = new RequestParamsParser().parse(rawRequest);

        assertEquals(0, params.size());
    }

    public void test_it_does_not_assign_any_params_if_none_are_found_in_the_body() throws Exception {
        byte[] rawRequest = "POST /pie HTTP/1.1\r\n\r\n".getBytes();

        Map<String, String> params = new RequestParamsParser().parse(rawRequest);

        assertEquals(0, params.size());
    }

    public void test_it_does_not_assign_any_params_if_passed_null() throws Exception {
        byte[] nullRequest = null;

        Map<String, String> params = new RequestParamsParser().parse(nullRequest);

        assertEquals(0, params.size());
    }
}
