package CobSpecApp;

import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.util.HashMap;

public class GetHandlerTest extends TestCase {
    public void test_it_work() throws Exception {
        assertEquals(1, 1);
    }
    //    public void test_it_handles_redirect() throws Exception {
//        HashMap params = new HashMap();
//        params.put("action", "GET");
//        params.put("path", "/redirect");
//        GetHandler handler = new GetHandler(new Setup(new String[0]));
//
//        Response response = handler.handle(params);
//
//        assertEquals("HTTP/1.1 302 FOUND\r\n", new String(response.getHeader(), "UTF-8"));
//    }
}
