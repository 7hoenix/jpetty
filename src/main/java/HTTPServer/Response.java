package HTTPServer;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private int statusCode;
    private Map<String, String> headers;
    private byte[] body;

    public Response(int statusCode) {
        this(statusCode, new HashMap<>(), new byte[0]);
    }

    private Response(int statusCode, Map<String, String> headers, byte[] body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public Response setStatusCode(int statusCode) {
        return new Response(statusCode, this.headers, this.body);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Response setHeader(String fieldName, String fieldValue) {
        headers.put(fieldName, fieldValue);
        return new Response(this.statusCode, headers, this.body);
    }

    public String getHeader(String fieldName) {
        return headers.get(fieldName);
    }

    public Response setBody(byte[] body) {
        return new Response(this.statusCode, this.headers, body);
    }

    public byte[] getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
