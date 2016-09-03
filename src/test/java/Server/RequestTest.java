package Server;

import HTTPServer.Request;
import junit.framework.TestCase;

import java.util.Map;

public class RequestTest extends TestCase {
    public void test_it_knows_its_headers() throws Exception {
        Request request = getRequest("/");

        assertEquals("POST / HTTP/1.1\r\nContent-Length: 13\r\nContent-Type: application/x-www-form-urlencoded\r\n", request.getHeader());
        assertEquals("hi=mom", request.getBody());
        assertEquals("POST / HTTP/1.1\r\nContent-Length: 13\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\nhi=mom", new String(request.getFullRequest(), "UTF-8"));
    }

    public void test_it_parses_the_header_to_return_the_action() throws Exception {
        Request request = getRequest();

        assertEquals("GET", request.findAction());
    }

    public void test_it_parses_the_header_to_return_the_scheme() throws Exception {
        Request request = getRequest();

        assertEquals("HTTP/1.1", request.findScheme());
    }

    public void test_it_parses_the_header_to_return_the_query_string() throws Exception {
        Request request = getRequest();

        assertEquals("/", request.findQuery());
    }

    public void test_it_returns_true_if_the_request_is_missing_a_header() throws Exception {
        byte[] fullRequest = ("\r\n\r\nhi mom\r\n").getBytes();
        String header = "";
        String body = "hi mom";
        Request request = new Request(fullRequest, header, body);

        assertEquals(false, request.isValid());
    }

    public void test_it_returns_false_if_the_request_is_only_a_single_line() throws Exception {
        byte[] fullRequest = ("GET / HTTP/1.1\r\n\r\n").getBytes();
        String header = "GET / HTTP/1.1";
        String body = "";
        Request request = new Request(fullRequest, header, body);

        assertEquals(true, request.isValid());
    }

    public void test_it_parses_the_query_string_to_return_decoded_params() throws Exception {
        Request request = getRequest("/parameters?a=1&b=2");

        Map params = request.findQueryParams();

        assertEquals("1", params.get("a"));
        assertEquals("2", params.get("b"));
    }

    public void test_it_parses_the_query_string_to_return_decoded_params_with_complicated_params() throws Exception {
        Request request = getRequest("/parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C" +
                "%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&var" +
                "iable_2=stuff");

        Map params = request.findQueryParams();

        assertEquals("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?", params.get("variable_1"));
    }
//
//    public void test_it_parses_params_in_the_body_of_a_post_request() throws Exception {
//        byte[] fullRequest = ("POST /form HTTP/1.1\r\n\r\na=1&b=2").getBytes();
//        String header = "POST /form HTTP/1.1";
//        String body = "a=1&b=2";
//        Request request = new Request(fullRequest, header, body);
//
//        Map params = request.findPostParams();
//
//        assertEquals("1", params.get("a"));
//        assertEquals("2", params.get("b"));
//        Response response = new Response();
//        response.setStatusCode(200).setHeader("Location", "");
//    }

    private Request getRequest(String query) {
        byte[] fullRequest = ("POST " + query + " HTTP/1.1\r\nContent-Length: 13\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\nhi=mom").getBytes();
        String header = new String("POST " + query + " HTTP/1.1\r\nContent-Length: 13\r\nContent-Type: application/x-www-form-urlencoded\r\n");
        String body = new String("hi=mom");

        return new Request(fullRequest, header, body);
    }

    private Request getRequest(String method, String query) {
        byte[] fullRequest = (method + " " + query + " HTTP/1.1\r\n\r\nhi mom\r\n").getBytes();
        String header = method + " " + query + " HTTP/1.1";
        String body = "hi mom";
        return new Request(fullRequest, header, body);
    }

    private Request getRequest() {
        return getRequest("GET", "/");
    }
}
