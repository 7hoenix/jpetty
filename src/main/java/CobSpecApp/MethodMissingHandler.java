package CobSpecApp;

import HTTPServer.*;

import java.io.IOException;

public class MethodMissingHandler implements Handler {
    private Settings settings;
    private DataStorage dataStore;

    public MethodMissingHandler(Settings settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        if (request.getAction().contains("GET")) {
            return new GetHandler(settings, dataStore).handle(request);
        } else {
            return new Response(405);
        }
    }
}
