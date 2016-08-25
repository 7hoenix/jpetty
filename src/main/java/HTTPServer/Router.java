package HTTPServer;
import CobSpecApp.ErrorHandler;
import CobSpecApp.Handler;

import java.io.IOException;
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
            return new ErrorHandler("HTTP/1.1 404 NOT FOUND\r\n");
        }
    }
}
