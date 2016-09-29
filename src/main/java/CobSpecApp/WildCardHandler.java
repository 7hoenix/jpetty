package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;
import HTTPServer.Utils.HandlerUtils;

public class WildCardHandler implements Handler {
    public Response handle(Request request) {
        return HandlerUtils.redirect("https://giphy.com/gifs/7yTqXVALy7Fwk");
    }
}
