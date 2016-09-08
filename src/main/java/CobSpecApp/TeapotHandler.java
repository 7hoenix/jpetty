package CobSpecApp;

import HTTPServer.*;

public class TeapotHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public TeapotHandler(Setup settings, DataStorage dataStore) {
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
