package CobSpecApp;

import HTTPServer.*;

public class OptionsHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public OptionsHandler(Setup settings) {
        this.settings = settings;
    }

    public OptionsHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) {
        if (request.getRoute().equals("/method_options")) {
            return new Response(200).setHeader("Allow", "GET,HEAD,POST,OPTIONS,PUT");
        } else {
            return new Response(200).setHeader("Allow", "GET,OPTIONS,PUT");
        }
    }
}
