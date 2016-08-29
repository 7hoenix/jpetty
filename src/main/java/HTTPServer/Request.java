package HTTPServer;

import java.util.Map;

public class Request {
    private Map params;

    public Request(Map params) {
        this.params = params;
    }

    public Map getParams() {
        return params;
    }
}
