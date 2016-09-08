package HTTPServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router2 {
    private Map<String, Map<String, Handler>> routes;

    public Router2() {
        this(new HashMap<String, Map<String, Handler>>());
    }

    private Router2(Map routes) {
        this.routes = routes;
    }

    public Response route(Request request) throws IOException {
        if (routeIsPresent(request)) {
            Handler handler = routes.get(request.getPath()).get(request.getAction());
            return handler.handle(request);
        } else {
            return new Response(404);
        }
    }

    public Handler get(String path, String action) {
        Handler handler = null;
        if (routes.get(path) != null && routes.get(path).get(action) != null)
            handler = routes.get(path).get(action);
        return handler;
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
