package CobSpecApp;

import HTTPServer.*;

import java.io.*;
import java.util.Map;

public class FormHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public FormHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        if (request.findAction().contains("GET")) {
            return new Response(200).setBody(formContent());
        } else if (request.findAction().contains("POST")) {
            Map params = request.findPostParams();
            dataStore.store("data", (String) params.get("data"));
        } else if (request.findAction().contains("PUT")) {
            Map params = request.findPostParams();
            dataStore.store("data", (String) params.get("data"));
        } else if (request.findAction().contains("DELETE")) {
            dataStore.remove("data");
        }
        return new Response(200);
    }

    private byte[] formContent() {
        if (!dataStore.retrieve("data").isEmpty()) {
            String data = "data=" + dataStore.retrieve("data");
            return basicForm(data).getBytes();
        } else {
            return basicForm().getBytes();
        }
    }

    private String basicForm(String value) {
        return "<!DOCTYPE html><html lang=\"en\"><body><form action=\"/form\" method=\"post\">" +
                "<input name=\"data\" type=\"text\">" +
                "<input type=\"submit\" value=\"submit\">" +
                "</form>" + value + "</body></html>";
    }

    private String basicForm() {
        return basicForm("");
    }
}
