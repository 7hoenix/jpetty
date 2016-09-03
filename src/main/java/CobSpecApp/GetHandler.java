package CobSpecApp;

import HTTPServer.*;

import java.io.IOException;

public class GetHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public GetHandler(Setup settings) {
        this.settings = settings;
    }

    public GetHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        if (request.getRoute().contains("/redirect")) {
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
