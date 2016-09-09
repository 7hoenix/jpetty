package CobSpecApp;

import HTTPServer.*;

public class PutHandler implements Handler {
    private Settings settings;
    private DataStorage dataStore;

    public PutHandler(Settings settings) {
        this.settings = settings;
        this.dataStore = new DataStore();
    }

    public PutHandler(Settings settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) {
        dataStore.store("PUT", request.getPath());
        return new Response(200);
    }
}
