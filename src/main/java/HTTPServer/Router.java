package HTTPServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router implements Handler {
    private Map<String, Map<String, Handler>> routes;

    public Router() {
        this(new HashMap<String, Map<String, Handler>>());
    }

    private Router(Map routes) {
        this.routes = routes;
    }

    @Override
    public Response handle(Request request) throws IOException {
        if (request != null && getRoute(request) != null) {
            return getRoute(request).handle(request);
        } else if (request != null && request.getAction().contains("OPTIONS")) {
            return handleOptionsRequest(request);
        } else {
            return new Response(404);
        }
    }

    private Response handleOptionsRequest(Request request) {
        return new Response(200)
                .setHeader("Allow", getValidActions(request.getPath()));
    }

    public Handler getRoute(Request request) {
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

    public Router setRoute(String path, String action, Handler handler) {
        Map routes = new HashMap<String, Handler>();
        Map updatedRoutes = new HashMap(this.routes);
        routes.put(action, handler);
        updatedRoutes.put(path, routes);
        return new Router(updatedRoutes);
    }

    public Router setRoute(String path, String[] actions, Handler handler) {
        Map routes = new HashMap<String, Handler>();
        Map updatedRoutes = new HashMap(this.routes);
        for(String action : actions) {
            routes.put(action, handler);
        }
        updatedRoutes.put(path, routes);
        return new Router(updatedRoutes);
    }

    private boolean routeIsPresent(Request request) {
        return (routes.get(request.getPath()) != null &&
                routes.get(request.getPath()).get(request.getAction()) != null);
    }
}
