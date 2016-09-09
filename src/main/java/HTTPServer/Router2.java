package HTTPServer;

import CobSpecApp.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router2 {
    private Map<String, Map<String, Handler>> routes;
    private Settings settings;

    public Router2() {
        this(new HashMap<String, Map<String, Handler>>());
    }

    public Router2(Settings settings) {
        this.settings = settings;
    }

    private Router2(Map routes) {
        this.routes = routes;
    }

    public Response route(Request request) throws IOException {
        Handler handler = getHandler(request);
        if (handler != null) {
            return handler.handle(request);
        } else {
            return new BasicHandler(settings).handle(request);
        }
    }

    public Handler getHandler(Request request) {
        Handler handler = null;
        if (routeIsPresent(request)) {
            handler = routes.get(request.getPath()).get(request.getAction());
        } else if (routeMatchesFile(request)) {
            handler = new FileHandler(settings);
        }
        return handler;
    }

    private boolean routeMatchesFile(Request request) {
        return (new File(settings.getRoot().getPath().concat(request.getPath())) != null);
    }

    public Router2 add(String path, String action, Handler handler) {
        Map routes = new HashMap<String, Handler>();
        Map updatedRoutes = new HashMap(this.routes);
        routes.put(action, handler);
        updatedRoutes.put(path, routes);
        return new Router2(updatedRoutes);
    }

    public Router2 add(String path, String[] actions, Handler handler) {
        Map routes = new HashMap<String, Handler>();
        Map updatedRoutes = new HashMap(this.routes);
        for(String action : actions) {
            routes.put(action, handler);
        }
        updatedRoutes.put(path, routes);
        return new Router2(updatedRoutes);
    }

    private boolean routeIsPresent(Request request) {
        return (routes.get(request.getPath()) != null &&
                routes.get(request.getPath()).get(request.getAction()) != null);
    }
}
