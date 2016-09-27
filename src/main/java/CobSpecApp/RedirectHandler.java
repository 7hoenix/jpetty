package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;

import java.io.IOException;
import java.util.Map;

public class RedirectHandler implements Handler {
    private Map<String, String> redirections;

    public RedirectHandler(Map<String, String> redirections) {
        this.redirections = redirections;
    }

    public Response handle(Request request) throws IOException {
        return new Response(302)
                .setHeader("Location", redirections.get(request.getPath()));
    }
}
