package CobSpecApp;

import HTTPServer.*;

public class PutHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public PutHandler(Setup settings) {
        this.settings = settings;
        this.dataStore = new DataStore();
    }

    public PutHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) {
        dataStore.store("PUT", request.getRoute());
        return new Response(200);
    }
}
