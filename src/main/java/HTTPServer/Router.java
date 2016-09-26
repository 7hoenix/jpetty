package HTTPServer;

import HTTPServer.Handlers.BasicHandler;
import HTTPServer.Handlers.Handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Map<String, Handler>> routes;
    private Settings settings;
    private ArrayList<String> log;

    public Router() {
        this(new HashMap<String, Map<String, Handler>>(), new Settings(), new ArrayList<>());
    }

    public Router(Settings settings, ArrayList<String> log) {
        this(new HashMap<String, Map<String, Handler>>(), settings, log);
    }

    private Router(Map routes, Settings settings, ArrayList<String> log) {
        this.routes = routes;
        this.settings = settings;
        this.log = log;
    }

    public Response route(Request request) throws IOException {
        if (request != null && getHandler(request) != null) {
            return getHandler(request).handle(request);
        } else {
            return new BasicHandler(settings, this, log).handle(request);
        }
    }

    public Handler getHandler(Request request) {
        return (routeIsPresent(request)) ? routes.get(request.getPath()).get(request.getAction()) : null;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public String getValidActions(String path) {
        String actions = "";
        Map actionRoutes = routes.get(path);
        for(Object action : actionRoutes.keySet()) {
            actions += action + ",";
        }
        actions += "OPTIONS";
        return actions;
    }

    public Router add(String path, String action, Handler handler) {
        Map routes = new HashMap<String, Handler>();
        Map updatedRoutes = new HashMap(this.routes);
        routes.put(action, handler);
        updatedRoutes.put(path, routes);
        return new Router(updatedRoutes, this.settings, this.log);
    }

    public Router add(String path, String[] actions, Handler handler) {
        Map routes = new HashMap<String, Handler>();
        Map updatedRoutes = new HashMap(this.routes);
        for(String action : actions) {
            routes.put(action, handler);
        }
        updatedRoutes.put(path, routes);
        return new Router(updatedRoutes, this.settings, this.log);
    }

    private boolean routeIsPresent(Request request) {
        return (routes.get(request.getPath()) != null &&
                routes.get(request.getPath()).get(request.getAction()) != null);
    }
}
