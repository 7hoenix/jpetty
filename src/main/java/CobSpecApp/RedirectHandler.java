package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;
import HTTPServer.Utils.HandlerUtils;

import java.io.IOException;
import java.util.Map;

public class RedirectHandler implements Handler {
    private Map<String, String> redirections;

    public RedirectHandler(Map<String, String> redirections) {
        this.redirections = redirections;
    }

    public Response handle(Request request) throws IOException {
        return HandlerUtils.redirect(redirections.get(request.getPath()));
    }
}
