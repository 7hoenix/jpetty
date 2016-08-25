package HTTPServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private Setup settings;
    private final Map routes;

    public Router(Setup settings) {
        this.settings = settings;
        this.routes = defaultRoutes();
    }

    public Router(Map routes) {
        this.settings = new Setup(new String[0]);
        this.routes = routes;
    }

    public Handler route(Map params) throws IOException {
        return findRoute((String) params.get("action"));
    }

    private Handler findRoute(String action) {
        if (routes.containsKey(action)) {
            return (Handler) routes.get(action);
        } else {
            return new ErrorHandler(settings);
        }
    }

    public Map defaultRoutes() {
        HashMap<String, Handler> routes = new HashMap();
        routes.put("GET", new GetHandler(settings));
        routes.put("HEAD", new HeadHandler(settings));
        routes.put("OPTIONS", new OptionsHandler(settings));
        return routes;
    }
}
