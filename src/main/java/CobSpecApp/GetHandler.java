package CobSpecApp;

import HTTPServer.*;

import java.io.IOException;

public class GetHandler implements Handler {
    private Settings settings;
    private DataStorage dataStore;

    public GetHandler(Settings settings) {
        this.settings = settings;
        this.dataStore = new DataStore();
    }

    public GetHandler(Settings settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        dataStore.store("GET", request.getPath());
        if (request.getPath().contains("/redirect")) {
            Response response = new Response(302).setHeader("Location", fullPath("/"));
            return response;
        } else if (request.getParam("variable_1") != null) {
            byte[] body =  ("variable_1 = " + request.getParam("variable_1") + "\r\n" +
                    "variable_2 = " + request.getParam("variable_2")).getBytes();
            return new Response(200).setBody(body);
        } else {
            return new FileHandler(settings).handle(request);
        }
    }

    private String fullPath(String path) {
        return "http://localhost:" + String.valueOf(settings.getPort() + path);
    }
}
