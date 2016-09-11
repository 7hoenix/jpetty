package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;

import java.io.IOException;
import java.util.Map;

public class ParameterHandler implements Handler {
    private Settings settings;
    private DataStorage dataStore;

    public ParameterHandler(Settings settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        return new Response(302)
                .setBody(findParams(request));
    }

    private byte[] findParams(Request request) {
        Map<String, String> params = request.getParams();
        String result = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result += entry.getKey() + " = " + entry.getValue() + "\r\n";
        }
        return result.getBytes();
    }
}
