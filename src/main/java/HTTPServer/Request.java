package HTTPServer;

import java.util.Map;

public class Request {
    private Map<String, String> params;
    private String header;
    private String body;

    public Request(Map params) {
        this.params = params;
    }

    public Request(Map params, String header, String body) {
        this.params = params;
        this.header = header;
        this.body = body;
    }

    public Map getParams() {
        return params;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }
}
