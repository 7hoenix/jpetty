package CobSpecApp;

import HTTPServer.*;

public class PostHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public PostHandler(Setup settings) {
        this.settings = settings;
    }

    public PostHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) {
        Response response = new Response();
        response.setHeader("HTTP/1.1 200 OK\r\n".getBytes());
        return response;
    }
}
