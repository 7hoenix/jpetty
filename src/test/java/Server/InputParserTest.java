package Server;

import HTTPServer.InputParser;
import HTTPServer.Request;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class InputParserTest extends TestCase {
    public void testItSeparatesLineIntoActionPathAndScheme() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());
        InputParser parser = new InputParser();

        Request request = parser.create(inputStream);

        assertEquals(request.getParams().get("action"), "GET");
        assertEquals(request.getParams().get("path"), "/");
        assertEquals(request.getParams().get("scheme"), "HTTP/1.1");
    }

    public void test_it_can_handle_a_blank_input_stream() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        InputParser parser = new InputParser();

        Request request = parser.create(inputStream);

        assert(request.getParams().isEmpty());
    }

    public void test_it_can_store_the_body_of_the_request() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET /parameters?a=1&b=2 HTTP/1.1".getBytes());
        InputParser parser = new InputParser();

        Request request = parser.create(inputStream);

        Map params = new HashMap();
        params.put("a", "1");
        params.put("b", "2");
        assertEquals("/parameters", request.getParams().get("path"));
        assertEquals("1", request.getParams().get("a"));
    }

    public void test_it_can_handle_a_complicated_case() throws Exception {
        InputStream inputStream = new ByteArrayInputStream(("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%" +
                "3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22" +
                        "is%20that%20all%22%3F&variable_2=stuff HTTP/1.1").getBytes());
        InputParser parser = new InputParser();

        Request request = parser.create(inputStream);

        assertEquals("Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?", request.getParams().get("variable_1"));
    }
}
