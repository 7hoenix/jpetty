package Server.Handlers;

import HTTPServer.*;
import HTTPServer.Handlers.AuthorizationHandler;
import HTTPServer.Handlers.Handler;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Base64;

public class AuthorizationHandlerTest extends TestCase {
    public void test_it_returns_a_401_if_unauthorized() throws Exception {
        Request request = new Request("/logs", "GET");
        Handler handler = new AuthorizationHandler(new Settings(), new ArrayList<String>());

        Response response = handler.handle(request);

        assertEquals(401, response.getStatusCode());
    }

    public void test_it_returns_200_if_authorized() throws Exception {
        String encoded = Base64.getEncoder().encodeToString("admin:hunter2".getBytes());
        Request request = new Request("GET", "/logs")
                .setHeader("Authorization", "basic " + encoded);
        ArrayList<String> log = new ArrayList<>();
        log.add("HTTP/1.1 GET /log");
        log.add("HTTP/1.1 PUT /these");
        log.add("HTTP/1.1 HEAD /requests");
        Handler handler = new AuthorizationHandler(new Settings(), log);

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
        String expected = "HTTP/1.1 GET /log\r\n" + "HTTP/1.1 PUT /these\r\n" + "HTTP/1.1 HEAD /requests\r\n";
        assertEquals(expected, new String(response.getBody(), "UTF-8"));
    }
}
