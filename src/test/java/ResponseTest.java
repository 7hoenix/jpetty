import HTTPServer.Response;
import junit.framework.TestCase;

import java.io.File;

/**
 * Created by jphoenix on 8/24/16.
 */
public class ResponseTest extends TestCase {
    public void test_it_can_wrap_content_type() throws Exception {
        File currentFile = new File("otherPublic/thing.txt");
        byte[] initialResponse = new byte[0];
        Response response = new Response();

        byte[] fullResponse = response.wrapContentType(initialResponse, currentFile);

        assertEquals("Content-Type: text/plain\r\n", new String(fullResponse, "UTF-8"));
    }

    public void test_it_returns_the_response_if_no_type_is_found() throws Exception {
        File currentFile = new File("otherPublic/fakeDirectory");
        byte[] initialResponse = "HTTP/1.1 200 OK\r\n".getBytes();
        Response response = new Response();

        byte[] fullResponse = response.wrapContentType(initialResponse, currentFile);

        assertEquals("HTTP/1.1 200 OK\r\n", new String(fullResponse, "UTF-8"));
    }

    public void test_it_can_wrap_content_length() throws Exception {
        File currentFile = new File("otherPublic/thing.txt");
        byte[] initialResponse = "HTTP/1.1 200 OK\r\n".getBytes();
        Response response = new Response();

        byte[] fullResponse = response.wrapContentLength(initialResponse, currentFile);

        assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 9\r\n", new String(fullResponse, "UTF-8"));
    }

    public void test_it_returns_the_response_if_no_length_is_found() throws Exception {
        File currentFile = new File("otherPublic/fakeDirectory");
        byte[] initialResponse = "HTTP/1.1 200 OK\r\n".getBytes();
        Response response = new Response();

        byte[] fullResponse = response.wrapContentLength(initialResponse, currentFile);

        assertEquals("HTTP/1.1 200 OK\r\n", new String(fullResponse, "UTF-8"));
    }
}
