package HTTPServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private final Map routes;

    public Router(Map routes) {
        this.routes = routes;
    }

    public Handler route(Map params) throws IOException {
        if (routes.containsKey(params.get("action"))) {
            return (Handler) routes.get(params.get("action"));
        } else {
            return (Handler) routes.get("ERROR");
        }
    }
}
