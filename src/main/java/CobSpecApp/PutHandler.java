package CobSpecApp;

import HTTPServer.*;

public class PutHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public PutHandler(Setup settings) {
        this.settings = settings;
    }

    public PutHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) {
        byte[] header = "HTTP/1.1 200 OK\r\n".getBytes();
        return new ResponseFactory().create(header);
    }
}
