package Server;

import HTTPServer.SocketWrapper;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketWrapperTest extends TestCase {
    public void test_it_knows_its_input_and_output_streams() throws Exception {
        OutputStream output = new ByteArrayOutputStream();
        InputStream input = new ByteArrayInputStream("".getBytes());
        Socket socket = new OtherSocket(input, output);

        SocketWrapper wrapper = new SocketWrapper(socket);

        assertEquals(input, wrapper.getInputStream());
        assertEquals(output, wrapper.getOutputStream());
    }

    public void test_it_can_be_closed() throws Exception {
        OutputStream output = new ByteArrayOutputStream();
        InputStream input = new ByteArrayInputStream("".getBytes());
        Socket socket = new OtherSocket(input, output);
        SocketWrapper wrapper = new SocketWrapper(socket);

        wrapper.close();

        assertNotSame(input, wrapper.getInputStream());
    }

    private class OtherSocket extends Socket {
        private InputStream input;
        private OutputStream output;
        public boolean open = true;

        public OtherSocket(InputStream input, OutputStream output) {
            this.input = input;
            this.output = output;
        }

        public InputStream getInputStream() {
            return (open) ? input : new ByteArrayInputStream("NOT LEGIT".getBytes());
        }

        public OutputStream getOutputStream() {
            return output;
        }

        public void close() {
            this.open = false;
        }
    }
}
