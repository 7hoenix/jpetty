package Server;

import HTTPServer.Request;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class RequestTest extends TestCase {
    public void test_it_knows_its_path_and_method() throws Exception {
        Request request = new Request("/", "GET");

        assertEquals("GET", request.getAction());
        assertEquals("/", request.getPath());
    }

    public void test_it_can_set_action() throws Exception {
        Request request = new Request("/", "GET");

        Request updatedRequest = request.setAction("POST");

        assertEquals("POST", updatedRequest.getAction());
    }

    public void test_action_is_immutable() throws Exception {
        Request request = new Request("/", "GET");

        request.setAction("POST");

        assertNotSame("POST", request.getAction());
    }

    public void test_it_can_set_route() throws Exception {
        Request request = new Request("/", "GET");

        Request updatedRequest = request.setPath("/games");

        assertEquals("/games", updatedRequest.getPath());
    }

    public void test_route_is_immutable() throws Exception {
        Request request = new Request("/", "GET");

        request.setAction("POST");

        assertNotSame("POST", request.getAction());
    }

    public void test_it_can_add_headers() throws Exception {
        Request request = new Request("/", "GET");

        Request updatedRequest = request.setHeader("Content-Length", "30").setHeader("Keep", "alive");

        assertEquals("30", updatedRequest.getHeader("Content-Length"));
        assertEquals("alive", updatedRequest.getHeader("Keep"));
    }

    public void test_headers_are_immutable() throws Exception {
        Request request = new Request("/", "GET");

        request.setHeader("Content-Length", "30");

        assertNotSame("30", request.getHeader("Content-Length"));
    }

    public void test_it_can_add_params() throws Exception {
        Request request = new Request("/", "GET");

        Request updatedRequest = request.setParam("a", "1");

        assertEquals("1", updatedRequest.getParam("a"));
    }

    public void test_params_are_immutable() throws Exception {
        Request request = new Request("/", "GET");

        request.setParam("a", "1");

        assertNotSame("1", request.getParam("a"));
    }

    public void test_it_can_add_multiple_headers_at_once() throws Exception {
        Request request = new Request("/", "GET");
        Map headers = new HashMap<String, String>();
        headers.put("Content-Length", "30");
        headers.put("Keep", "alive");

        Request updatedRequest = request.setHeaders(headers);

        assertEquals("30", updatedRequest.getHeader("Content-Length"));
        assertEquals("alive", updatedRequest.getHeader("Keep"));
    }

    public void test_it_can_add_multiple_params_at_once() throws Exception {
        Request request = new Request("/", "GET");
        Map params = new HashMap<String, String>();
        params.put("a", "1");
        params.put("b", "2");

        Request updatedRequest = request.setParams(params);

        assertEquals("1", updatedRequest.getParam("a"));
        assertEquals("2", updatedRequest.getParam("b"));
    }
}
