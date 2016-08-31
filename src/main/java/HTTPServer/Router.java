package HTTPServer;
import CobSpecApp.ErrorHandler;

import java.io.IOException;
import java.util.Map;

public class Router {
    private final Map<String, Handler> routes;

    public Router(Map<String, Handler> routes) {
        this.routes = routes;
    }

    public Handler route(Request request) throws IOException {
        if (routes.containsKey(request.findQuery())) {
            return routes.get(request.findQuery());
        } else if (routes.containsKey(request.findAction())) {
            return routes.get(request.findAction());
        } else {
            return new ErrorHandler("HTTP/1.1 405 NOT FOUND\r\n");
        }
    }
}
