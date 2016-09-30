package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;

public class TeapotHandler implements Handler {
    public Response handle(Request request) {
        if (request.getPath().contains("/coffee")) {
            return new Response(418).setBody("I'm a teapot".getBytes());
        } else {
            return new Response(200);
        }
    }
}
