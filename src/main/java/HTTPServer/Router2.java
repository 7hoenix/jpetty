package HTTPServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router2 {
    private Map<String, Map<String, Handler>> routes;
    private Settings settings;

    public Router2() {
        this(new HashMap<String, Map<String, Handler>>(), new Settings());
    }

    public Router2(Settings settings) {
        this(new HashMap<String, Map<String, Handler>>(), settings);
    }

    private Router2(Map routes, Settings settings) {
        this.routes = routes;
        this.settings = settings;
    }

    public Response route(Request request) throws IOException {
        if (request != null && getHandler(request) != null) {
            return getHandler(request).handle(request);
        } else {
            return new BasicHandler(settings).handle(request);
        }
    }

    public Handler getHandler(Request request) {
        return (routeIsPresent(request)) ? routes.get(request.getPath()).get(request.getAction()) : null;
    }

    public Router2 add(String path, String action, Handler handler) {
        Map routes = new HashMap<String, Handler>();
        Map updatedRoutes = new HashMap(this.routes);
        routes.put(action, handler);
        updatedRoutes.put(path, routes);
        return new Router2(updatedRoutes, this.settings);
    }

    public Router2 add(String path, String[] actions, Handler handler) {
        Map routes = new HashMap<String, Handler>();
        Map updatedRoutes = new HashMap(this.routes);
        for(String action : actions) {
            routes.put(action, handler);
        }
        updatedRoutes.put(path, routes);
        return new Router2(updatedRoutes, this.settings);
    }

    private boolean routeIsPresent(Request request) {
        return (routes.get(request.getPath()) != null &&
                routes.get(request.getPath()).get(request.getAction()) != null);
    }
}
