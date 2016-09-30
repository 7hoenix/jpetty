package Server.Utils;

import HTTPServer.Response;
import HTTPServer.Utils.HandlerUtils;
import junit.framework.TestCase;

public class HandlerUtilsTest extends TestCase {
    public void test_it_generates_a_redirect_response() throws Exception {
        Response response = HandlerUtils.redirect("http://google.com");

        assertEquals(302, response.getStatusCode());
        assertEquals("http://google.com", response.getHeader("Location"));
    }

    public void test_it_generates_a_forbidden_response() throws Exception {
        Response response = HandlerUtils.forbidden();

        assertEquals(403, response.getStatusCode());
    }
}
