package CobSpecApp;

import HTTPServer.*;

public class PostHandler implements Handler {
    private Settings settings;
    private DataStorage dataStore;

    public PostHandler(Settings settings) {
        this.settings = settings;
    }

    public PostHandler(Settings settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) {
        return new Response(200);
    }
}
