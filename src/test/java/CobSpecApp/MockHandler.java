package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;

public class MockHandler implements Handler {
    private Settings settings;
    private boolean called;

    public MockHandler(Settings settings) {
        this.settings = settings;
        this.called = false;
    }

    public Response handle(Request request) {
        this.called = true;
        return new Response(200);
    }
}
