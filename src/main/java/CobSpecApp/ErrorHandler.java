package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;
import HTTPServer.ResponseFactory;

public class ErrorHandler implements Handler {
    private String message;

    public ErrorHandler(String message) {
        this.message = message;
    }

    public Response handle(Request request) {
        return new ResponseFactory().create(message.getBytes());
    }
}
