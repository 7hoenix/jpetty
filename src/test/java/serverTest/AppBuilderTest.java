package serverTest;

import junit.framework.TestCase;
import server.ApplicationBuilder;
import server.BasicHandler;
import server.Handler;
import server.Middleware;
import server.Request;
import server.Response;

public class AppBuilderTest extends TestCase {
    public void test_it_can_wrap_an_application_in_middleware() throws Exception {
        Handler app = ApplicationBuilder.setHandler(new BasicHandler())
                .use(new MockMiddleware())
                .build();
        Request request = new Request("/", "GET");

        Response response = app.handle(request);

        assertEquals(160, response.getStatusCode());
    }

    public void test_it_applies_two_middlewares_in_reverse_order() throws Exception {
        Handler app = ApplicationBuilder.setHandler(new BasicHandler())
                .use(new MockMiddleware())
                .use(new MockMiddleware2())
                .build();
        Request request = new Request("/", "GET");

        Response response = app.handle(request);

        assertEquals(260, response.getStatusCode());
    }

    public class MockMiddleware implements Middleware {
        @Override
        public Handler myApply(Handler handler) {
            return new MockHandler(160);
        }
    }

    public class MockMiddleware2 implements Middleware {
        @Override
        public Handler myApply(Handler handler) {
            return new MockHandler(260);
        }
    }
}
