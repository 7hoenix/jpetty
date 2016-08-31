package Server;

import HTTPServer.Response;
import HTTPServer.ResponseFactory;
import junit.framework.TestCase;

public class ResponseFactoryTest extends TestCase {
    public void test_it_creates_a_new_response_object_with_byte_arrays() throws Exception {
        byte[] header = new byte[0];
        byte[] body = new byte[0];
        ResponseFactory factory = new ResponseFactory();

        Response response = factory.create(header, body);

        assertEquals(header, response.getHeader());
        assertEquals(body, response.getBody());
    }

    public void test_it_supports_not_having_a_body() throws Exception {
        byte[] header = new byte[0];
        ResponseFactory factory = new ResponseFactory();

        Response response = factory.create(header);

        assertEquals(header, response.getHeader());
        assertEquals("", new String(response.getBody(), "UTF-8"));
    }
}
