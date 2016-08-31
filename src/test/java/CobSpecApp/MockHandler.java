package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;
import HTTPServer.Setup;

public class MockHandler implements Handler {
    private Setup settings;
    private boolean called;

    public MockHandler(Setup settings) {
        this.settings = settings;
        this.called = false;
    }

    public Response handle(Request request) {
        this.called = true;
        return new Response(new byte[0]);
    }
}
