package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.RequestParser;
import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;

public class GetHandlerTest extends TestCase {
    public void test_it_handles_redirect() throws Exception {
        Request request = new RequestParser().parse(new ByteArrayInputStream("GET /redirect HTTP/1.1\r\n\r\n".getBytes()));
        GetHandler handler = new GetHandler(new Setup(new String[0]));

        Response response = handler.handle(request);

        assertEquals(302, response.getStatusCode());
        assertEquals("http://localhost:5000/", response.getHeader("Location"));
    }

    public void test_it_handles_decoding_parameters() throws Exception {
        Request request = new RequestParser().parse(new ByteArrayInputStream("GET /parameters?variable_1=cake&variable_2=nom%20nom HTTP/1.1\r\n\r\n".getBytes()));
        GetHandler handler = new GetHandler(new Setup(new String[0]));

        Response response = handler.handle(request);

        assertEquals("variable_1 = cake\r\nvariable_2 = nom nom", new String(response.getBody(), "UTF-8"));
    }
}
