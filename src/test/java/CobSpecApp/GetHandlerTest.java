package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.util.HashMap;

public class GetHandlerTest extends TestCase {
    public void test_it_handles_redirect() throws Exception {
        HashMap params = new HashMap();
        params.put("action", "GET");
        params.put("path", "/redirect");
        GetHandler handler = new GetHandler(new Setup(new String[0]));

        Response response = handler.handle(new Request(params));

        assertEquals("HTTP/1.1 302 FOUND\r\nLocation: http://localhost:5000/\r\n", new String(response.getHeader(), "UTF-8"));
    }

    public void test_it_handles_decoding_parameters() throws Exception {
        HashMap params = new HashMap();
        params.put("action", "GET");
        params.put("path", "/parameters");
        params.put("variable_1", "cake");
        params.put("variable_2", "nom nom");
        GetHandler handler = new GetHandler(new Setup(new String[0]));

        Response response = handler.handle(new Request(params));

        assertEquals("variable_1 = cake\r\nvariable_2 = nom nom", new String(response.getBody(), "UTF-8"));
    }
}
