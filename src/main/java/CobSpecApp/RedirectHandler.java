package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RedirectHandler implements Handler {
    private Settings settings;
    private Repository dataStore;
    private Map<String, String> redirections;

    public RedirectHandler(Settings settings, Repository dataStore) {
        this(settings, new HashMap<>(), dataStore);
    }

    public RedirectHandler(Settings settings, Map<String, String> redirections, Repository dataStore) {
        this.settings = settings;
        this.redirections = addDefaultRedirections(redirections);
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        return new Response(302)
                .setHeader("Location", fullPath(redirections.get(request.getPath())));
    }

    private Map<String, String> addDefaultRedirections(Map<String, String> redirections) {
        HashMap<String, String> result = new HashMap<>();
        result.put("/redirect", "/");
        result.putAll(redirections);
        return result;
    }

    private String fullPath(String path) {
        return "http://localhost:" + String.valueOf(settings.getPort() + path);
    }
}
