package CobSpecApp;

import HTTPServer.Response;

import java.util.Map;

public class ErrorHandler implements Handler {
    private String message;

    public ErrorHandler(String message) {
        this.message = message;
    }

    public Response handle(Map params) {
        Response response = new Response();
        response.setHeader(message.getBytes());
        return response;
    }
}
