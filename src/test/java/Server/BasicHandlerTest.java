package Server;

import HTTPServer.BasicHandler;
import HTTPServer.Request;
import HTTPServer.Response;
import junit.framework.TestCase;
import junit.framework.TestResult;

public class BasicHandlerTest extends TestCase {
    public void test_it_works() throws Exception {
        assertEquals(1, 1);
    }
    //    public void test_it_handles_not_valid() throws Exception {
//        Request request = new Request("GET", "/cake");
//        BasicHandler handler = new BasicHandler("public");
//
//        Response response = handler.handle(request);
//
//        assertEquals(404, response.getStatusCode());
//    }
}
