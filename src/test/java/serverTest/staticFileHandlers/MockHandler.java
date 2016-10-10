package serverTest.staticFileHandlers;

import server.Handler;
import server.Request;
import server.Response;

public class MockHandler implements Handler {
    private int statusCode;

    public MockHandler(int statusCode) {
        this.statusCode = statusCode;
    }

    public Response handle(Request request) {
        return new Response(statusCode);
    }
}
