package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.Response;

public class ErrorHandler implements Handler {
    private String message;

    public ErrorHandler(String message) {
        this.message = message;
    }

    public Response handle(Request request) {
        Response response = new Response();
        response.setHeader(message.getBytes());
        return response;
    }
}
