package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Repository;
import HTTPServer.Request;
import HTTPServer.Response;

import java.io.IOException;

public class FormHandler implements Handler {
    private Repository dataStore;

    public FormHandler(Repository dataStore) {
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        if (request.getAction().contains("GET")) {
            return new Response(200).setBody(formContent());
        } else if (request.getAction().contains("POST")) {
            dataStore.store("data", request.getParam("data"));
        } else if (request.getAction().contains("PUT")) {
            dataStore.store("data", request.getParam("data"));
        } else if (request.getAction().contains("DELETE")) {
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
