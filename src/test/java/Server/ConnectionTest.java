package Server;

import HTTPServer.Connection;
import HTTPServer.Response;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectionTest extends TestCase {
    public void test_it_can_create_a_new_connection() throws Exception {
        MockSocket socket = getMockSocket("GET /");
        new Connection(socket);

        assertEquals(true, socket.isOpen());
    }

    public void test_it_closes_down_the_connection_if_closed() throws Exception {
        MockSocket socket = getMockSocket("GET /");
        Connection connection = new Connection(socket);

        connection.close();

        assertEquals(false, socket.isOpen());
    }

    public void test_it_can_read_an_input_and_return_a_request() throws Exception {
        MockSocket socket = getMockSocket("GET /");
        Connection connection = new Connection(socket);

        connection.read();

        assertEquals(true, socket.isRead());
    }

    public void test_it_can_write_a_response_to_an_output_stream() throws Exception {
        MockSocket socket = getMockSocket("GET /");
        Connection connection = new Connection(socket);
        Response response = new Response(200);

        connection.write(response);

        assertEquals(true, socket.isWritten());
    }

    public void test_it_is_runnable() throws Exception {
        MockSocket socket = getMockSocket("GET /");
        Connection connection = new Connection(socket);

        connection.run();

        assertEquals(true, socket.isRead());
        assertEquals(true, socket.isWritten());
        assertEquals(false, socket.isOpen());
    }

    private MockSocket getMockSocket(String line) {
        InputStream input = new ByteArrayInputStream((line +  " HTTP/1.1\r\n\r\n").getBytes());
        OutputStream output = new ByteArrayOutputStream();
        return new MockSocket(input, output);
    }
}
