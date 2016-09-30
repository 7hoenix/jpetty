package Server.StaticFileHandlers;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;

public class MockHandler implements Handler {
    private int statusCode;

    public MockHandler(int statusCode) {
        this.statusCode = statusCode;
    }

    public Response handle(Request request) {
        return new Response(statusCode);
    }
}
