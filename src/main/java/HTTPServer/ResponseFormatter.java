package HTTPServer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseFormatter {
    public byte[] formatResponse(Response response) throws IOException {
        return combine(findHeader(response).getBytes(), response.getBody());
    }

    private String findHeader(Response response) {
        return "HTTP/1.1 " + formatStatus(response) + formatHeaders(response) + "\r\n";
    }

    private byte[] combine(byte[] original, byte[] addend) throws IOException {
        ByteArrayOutputStream combined = new ByteArrayOutputStream();
        combined.write(original);
        combined.write(addend);
        return combined.toByteArray();
    }

    private String formatHeaders(Response response) {
        String headers = "";
        for(Map.Entry<String, String> header : response.getHeaders().entrySet()) {
            headers += (header.getKey() + ": " + header.getValue() + "\r\n");
        }
        return headers;
    }

    private String formatStatus(Response response) {
        return String.valueOf(response.getStatusCode()) + " " + findStatusMessage(response.getStatusCode());
    }

    private String findStatusMessage(int statusCode) {
        Map<Integer, String> messagesByCodes = defaultMessages();
        return messagesByCodes.get(statusCode) + "\r\n";
    }

    private Map<Integer, String> defaultMessages() {
        Map<Integer, String> messagesByCodes = new HashMap();
        messagesByCodes.put(200, "OK");
        return messagesByCodes;
    }
}
