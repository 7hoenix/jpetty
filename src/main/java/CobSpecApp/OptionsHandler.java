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
        byte[] header;
        if (request.findQuery().equals("/method_options")) {
            header = "HTTP/1.1 200 OK\r\nAllow: GET,HEAD,POST,OPTIONS,PUT\r\n".getBytes();
        } else {
            header = "HTTP/1.1 200 OK\r\nAllow: GET,OPTIONS\r\n".getBytes();
        }
        return new ResponseFactory().create(header);
    }
}
