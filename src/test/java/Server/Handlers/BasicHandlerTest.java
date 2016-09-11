package Server.Handlers;

import HTTPServer.*;
import junit.framework.TestCase;
import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.Base64;

public class BasicHandlerTest extends TestCase {
    public void test_it_works() throws Exception {
        assertEquals(1, 1);
    }
//        public void test_it_handles_not_valid() throws Exception {
//        Request request = new Request("GET", "/cake");
//        BasicHandler handler = new BasicHandler("public");
//
//        Response response = handler.handle(request);
//
//        assertEquals(404, response.getStatusCode());
//    }
}
