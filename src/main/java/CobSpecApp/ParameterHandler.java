package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handler;

import java.io.IOException;
import java.util.Map;

public class ParameterHandler implements Handler {

    public Response handle(Request request) throws IOException {
        return new Response(200)
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
