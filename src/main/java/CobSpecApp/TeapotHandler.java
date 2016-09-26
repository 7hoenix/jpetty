package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;

public class TeapotHandler implements Handler {
    private Settings settings;
    private Repository dataStore;

    public TeapotHandler(Settings settings, Repository dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) {
        if (request.getPath().contains("/coffee")) {
            return new Response(418).setBody("I'm a teapot".getBytes());
        } else {
            return new Response(200);
        }
    }
}
