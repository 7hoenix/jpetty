package Server;

import HTTPServer.Responses;
import junit.framework.TestCase;

import java.io.File;

public class ResponsesTest extends TestCase {
    public void test_it_can_wrap_content_type() throws Exception {
        File currentFile = new File("otherPublic/thing.txt");
        byte[] initialResponse = new byte[0];
        Responses responses = new Responses();

        byte[] fullResponse = responses.wrapContentType(initialResponse, currentFile);

        assertEquals("Content-Type: text/plain\r\n", new String(fullResponse, "UTF-8"));
    }

    public void test_it_returns_the_response_if_no_type_is_found() throws Exception {
        File currentFile = new File("otherPublic/fakeDirectory");
        byte[] initialResponse = "HTTP/1.1 200 OK\r\n".getBytes();
        Responses responses = new Responses();

        byte[] fullResponse = responses.wrapContentType(initialResponse, currentFile);

        assertEquals("HTTP/1.1 200 OK\r\n", new String(fullResponse, "UTF-8"));
    }

    public void test_it_can_wrap_content_length() throws Exception {
        File currentFile = new File("otherPublic/thing.txt");
        byte[] initialResponse = "HTTP/1.1 200 OK\r\n".getBytes();
        Responses responses = new Responses();

        byte[] fullResponse = responses.wrapContentLength(initialResponse, currentFile);

        assertEquals("HTTP/1.1 200 OK\r\nContent-Length: 9\r\n", new String(fullResponse, "UTF-8"));
    }

    public void test_it_returns_the_response_if_no_length_is_found() throws Exception {
        File currentFile = new File("otherPublic/fakeDirectory");
        byte[] initialResponse = "HTTP/1.1 200 OK\r\n".getBytes();
        Responses responses = new Responses();

        byte[] fullResponse = responses.wrapContentLength(initialResponse, currentFile);

        assertEquals("HTTP/1.1 200 OK\r\n", new String(fullResponse, "UTF-8"));
    }

    public void test_it_can_return_a_basic_response() throws Exception {
        Responses responses = new Responses();

        assertEquals("HTTP/1.1 200 OK\r\n", new String(responses.basicResponse("1.1", "200", "OK"), "UTF-8"));
        assertEquals("HTTP/1.1 200 OK\r\n", new String(responses.basicResponse(), "UTF-8"));
    }

    public void test_it_can_return_a_connection_close_or_open() throws Exception {
        Responses responses = new Responses();

        assertEquals("Connection: close\r\n", new String(responses.wrapConnection(new byte[0]), "UTF-8"));
    }

    public void test_it_can_compose_responses() throws Exception {
        File currentFile = new File("otherPublic/thing.txt");
        Responses responses = new Responses();

        byte[] fullResponse = responses.wrapContentLength(responses.wrapContentType(responses.basicResponse(), currentFile), currentFile);

        assertEquals("HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: 9\r\n", new String(fullResponse, "UTF-8"));
    }

    public void test_it_wraps_body_content() throws Exception {
        File currentFile = new File("otherPublic/thing.txt");
        Responses responses = new Responses();

        byte[] fullResponse = responses.wrapBody(new byte[0], currentFile);

        assertEquals("HI there.", new String(fullResponse, "UTF-8"));

    }
}
