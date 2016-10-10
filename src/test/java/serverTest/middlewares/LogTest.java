package serverTest.middlewares;

import server.BasicHandler;
import server.Handler;
import server.Middleware;
import server.Request;
import server.Response;
import junit.framework.TestCase;
import server.middlewares.WrapRequestLog;

import java.util.ArrayList;

public class LogTest extends TestCase {
    public void test_it_returns_the_a_listing_of_the_visited_routes() throws Exception {
        Request request = new Request("/logs", "GET");
        ArrayList<String> log = new ArrayList<>();
        log.add("HTTP/1.1 GET /log");
        log.add("HTTP/1.1 PUT /these");
        log.add("HTTP/1.1 HEAD /requests");
        Middleware logger = new WrapRequestLog()
                .setLog(log);
        Handler logHandler = logger.apply(new BasicHandler());


        Response response = logHandler.handle(request);

        assertEquals(200, response.getStatusCode());
        String expected = "HTTP/1.1 GET /log\r\n" + "HTTP/1.1 PUT /these\r\n" + "HTTP/1.1 HEAD /requests\r\n";
        assertEquals(expected, new String(response.getBody(), "UTF-8"));
    }
}
