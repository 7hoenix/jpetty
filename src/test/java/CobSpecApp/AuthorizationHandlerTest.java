package CobSpecApp;

import HTTPServer.*;
import junit.framework.TestCase;

import java.util.Base64;

public class AuthorizationHandlerTest extends TestCase {
    public void test_it_returns_a_401_if_unauthorized() throws Exception {
        Request request = new Request("GET", "/logs");
        Handler handler = new AuthorizationHandler(new Settings(), new DataStore());

        Response response = handler.handle(request);

        assertEquals(401, response.getStatusCode());
        assertNotSame("GET /log HTTP/1.1", new String(response.getBody(), "UTF-8"));
    }

    public void test_it_returns_200_if_authorized() throws Exception {
        String encoded = Base64.getEncoder().encodeToString("admin:hunter2".getBytes());
        Request request = new Request("GET", "/logs")
                .setHeader("Authorization", "basic " + encoded);
        DataStorage repository = new DataStore();
        repository.store("GET", "/log");
        repository.store("PUT", "/these");
        repository.store("HEAD", "/requests");
        Handler handler = new AuthorizationHandler(new Settings(), repository);

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
        String expected = "GET /log HTTP/1.1\r\n" + "PUT /these HTTP/1.1\r\n" + "HEAD /requests HTTP/1.1\r\n";
        assertEquals(expected, new String(response.getBody(), "UTF-8"));
    }
}
