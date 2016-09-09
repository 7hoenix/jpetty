package CobSpecApp;

import HTTPServer.*;

public class OptionsHandler implements Handler {
    private Settings settings;
    private DataStorage dataStore;

    public OptionsHandler(Settings settings) {
        this.settings = settings;
    }

    public OptionsHandler(Settings settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) {
        if (request.getPath().equals("/method_options")) {
            return new Response(200).setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        } else {
            return new Response(200).setHeader("Allow", "GET,OPTIONS,PUT");
        }
    }
}
