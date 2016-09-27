package HTTPServer;

import HTTPServer.Handlers.BasicHandler;
import HTTPServer.Handlers.Handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Map<String, Handler>> routes;
    private ArrayList<String> log;
    private boolean autoIndex;

    public Router() {
        this(new HashMap<String, Map<String, Handler>>(), new ArrayList<>(), false);
    }

    public Router(ArrayList<String> log, boolean autoIndex) {
        this(new HashMap<String, Map<String, Handler>>(), log, autoIndex);
    }

    private Router(Map routes, ArrayList<String> log, boolean autoIndex) {
        this.routes = routes;
        this.autoIndex = autoIndex;
        this.log = log;
    }

    public Response route(Request request) throws IOException {
        if (request != null && getHandler(request) != null) {
            return getHandler(request).handle(request);
        } else if (request != null && request.getAction().contains("OPTIONS")) {
            return handleOptionsRequest(request);
        } else {
            return new BasicHandler(log, autoIndex).handle(request);
        }
    }

    private Response handleOptionsRequest(Request request) {
        return new Response(200)
                .setHeader("Allow", getValidActions(request.getPath()));
    }

    public Handler getHandler(Request request) {
        return (routeIsPresent(request)) ? routes.get(request.getPath()).get(request.getAction()) : null;
    }

    private String getValidActions(String path) {
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
        return new Router(updatedRoutes, this.log, this.autoIndex);
    }

    public Router add(String path, String[] actions, Handler handler) {
        Map routes = new HashMap<String, Handler>();
        Map updatedRoutes = new HashMap(this.routes);
        for(String action : actions) {
            routes.put(action, handler);
        }
        updatedRoutes.put(path, routes);
        return new Router(updatedRoutes, this.log, this.autoIndex);
    }

    private boolean routeIsPresent(Request request) {
        return (routes.get(request.getPath()) != null &&
                routes.get(request.getPath()).get(request.getAction()) != null);
    }
}
