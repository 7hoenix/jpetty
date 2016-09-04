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
        if (routes.containsKey(request.getRoute())) {
            return routes.get(request.getRoute());
        } else if (routes.containsKey(request.getAction())) {
            return routes.get(request.getAction());
        } else {
            return new ErrorHandler(404);
        }
    }
}
