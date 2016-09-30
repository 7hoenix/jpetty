package Server;

import HTTPServer.Response;
import junit.framework.TestCase;

public class ResponseTest extends TestCase {
    public void test_it_returns_the_status_code() throws Exception {
        Response response = new Response(200);

        assertEquals(200, response.getStatusCode());
    }

    public void test_it_can_set_the_status_code() throws Exception {
        Response response = new Response(200);

        Response updatedResponse = response.setStatusCode(404);

        assertEquals(404, updatedResponse.getStatusCode());
    }

    public void test_status_code_is_immutable() throws Exception {
        Response response = new Response(200);

        response.setStatusCode(404);

        assertNotSame(404, response.getStatusCode());
    }

    public void test_it_adds_a_new_header() throws Exception {
        Response response = new Response(200);

        Response updatedResponse = response.setHeader("Content-Type", "text/html");

        assertEquals("text/html", updatedResponse.getHeader("Content-Type"));
    }

    public void test_headers_is_immutable() throws Exception {
        Response response = new Response(200);

        response.setHeader("Content-Type", "text/html");

        assertNotSame("text/html", response.getHeader("Content-Type"));
    }

    public void test_it_can_set_the_body() throws Exception {
        Response response = new Response(200);

        Response updatedResponse = response.setBody("INSERT BODY HERE".getBytes());

        assertEquals("INSERT BODY HERE", new String(updatedResponse.getBody(), "UTF-8"));
    }

    public void test_body_is_immutable() throws Exception {
        Response response = new Response(200);

        response.setBody("INSERT BODY HERE".getBytes());

        assertNotSame("INSERT BODY HERE", new String(response.getBody(), "UTF-8"));
    }

    public void test_it_can_return_a_map_of_its_headers() throws Exception {
        Response response = new Response(200);

        Response updatedResponse = response.setHeader("Content-Type", "text/html").setHeader("Location", "/here");

        assertEquals("text/html", updatedResponse.getHeaders().get("Content-Type"));
    }
}
