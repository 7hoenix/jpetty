package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;

public class ErrorHandler implements Handler {
    private int statusCode;

    public ErrorHandler(int statusCode) {
        this.statusCode = statusCode;
    }

    public Response handle(Request request) {
        return new Response(statusCode);
    }
}
