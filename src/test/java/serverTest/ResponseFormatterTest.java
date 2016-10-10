package serverTest;

import junit.framework.TestCase;
import server.Response;
import server.ResponseFormatter;

public class ResponseFormatterTest extends TestCase {
    public void test_it_takes_a_response_object_and_returns_a_full_byte_array() throws Exception {
        Response response = new Response(200);

        byte[] formattedResponse = new ResponseFormatter().formatResponse(response);

        assertEquals("HTTP/1.1 200 OK\r\n\r\n", new String(formattedResponse, "UTF-8"));
    }

    public void test_it_takes_a_complex_object_and_returns_an_accurate_response() throws Exception {
        Response response = new Response(200);
        Response updatedResponse = response.setHeader("Location", "/here")
                .setHeader("Content-Type", "text/html")
                .setBody("<html><body>hello world</body></html>".getBytes());

        byte[] formattedResponse = new ResponseFormatter().formatResponse(updatedResponse);

        String expectedResponse = "HTTP/1.1 200 OK\r\nLocation: /here\r\nContent-Type: text/html\r\n\r\n" +
                "<html><body>hello world</body></html>";
        assertEquals(expectedResponse, new String(formattedResponse, "UTF-8"));
    }
}
