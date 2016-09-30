package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;

public class WildCardHandler implements Handler {
    public Response handle(Request request) {
        return new Response(404)
                .setHeader("Content-Type", "text/html")
                .setBody(("<!DOCTYPE html><html lang=\"en\"><body>" +
                        "<img src=\"https://media.giphy.com/media/7yTqXVALy7Fwk/giphy.gif\">" +
                        "</body></html>").getBytes());
    }
}
