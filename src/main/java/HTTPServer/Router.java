package HTTPServer;
import CobSpecApp.ErrorHandler;
import CobSpecApp.Handler;

import java.io.IOException;
import java.util.Map;

public class Router {
    private final Map<String, Handler> routes;

    public Router(Map<String, Handler> routes) {
        this.routes = routes;
    }

    public Handler route(Request request) throws IOException {
        if (routes.containsKey(request.getParams().get("action"))) {
            return routes.get(request.getParams().get("action"));
        } else {
            return new ErrorHandler("HTTP/1.1 405 NOT FOUND\r\n");
        }
    }
}
