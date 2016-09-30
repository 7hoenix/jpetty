package Server.Middleware;

import HTTPServer.Handler;
import HTTPServer.Middleware;
import HTTPServer.Middlewares.AuthorizationHandler;
import HTTPServer.Request;
import HTTPServer.Response;
import Server.StaticFileHandlers.MockHandler;
import junit.framework.TestCase;

import java.util.Base64;

public class AuthorizationTest extends TestCase {
    public void test_it_intercepts_a_protected_route_and_returns_a_401() throws Exception {
        Request request = new Request("/logs", "GET");
        Handler basicHandler = new MockHandler(200);
        Middleware auth = new AuthorizationHandler()
                .setUserName("passwordIsTaco")
                .setPassword("taco")
                .setRealm("jphoenx")
                .setProtectedRoutes(new String[] {"/logs"});
        Handler authHandler = auth.apply(basicHandler);

        Response response = authHandler.handle(request);

        assertEquals(401, response.getStatusCode());
        assertEquals("Basic realm=jphoenx", response.getHeader("WWW-Authenticate"));
    }

    public void test_it_does_allow_authorized_users_to_see_the_logs() throws Exception {
        String encoded = Base64.getEncoder().encodeToString("admin:hunter2".getBytes());
        Request request = new Request("/", "GET")
                .setHeader("Authorization", "basic " + encoded);
        Handler basicHandler = new MockHandler(200);
        Middleware auth = new AuthorizationHandler()
                .setUserName("admin")
                .setPassword("hunter2")
                .setRealm("jphoenx")
                .setProtectedRoutes(new String[] {"/"});
        Handler authHandler = auth.apply(basicHandler);

        Response response = authHandler.handle(request);

        assertEquals(200, response.getStatusCode());
    }
}
