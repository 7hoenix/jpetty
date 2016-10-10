package serverTest.utils;

import server.Response;
import junit.framework.TestCase;
import server.utils.HandlerUtils;

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
