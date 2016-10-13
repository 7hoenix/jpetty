package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Router implements Handler {
    private List<Route> routes;

    public Router() { this(new ArrayList<>()); }

    private Router(List<Route> routes) { this.routes = routes; }

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
        for(Route route : routes) {
            if (route.isAMatch(request.getPath(), request.getAction())) {
                return route.get(request.getPath(), request.getAction());
            }
        }
        return null;
    }

    private String getValidActions(String path) {
        List<String> actions = new ArrayList<>();
        for(Route route : routes) {
            if (route.getPath().equals(path)) {
                actions.add(route.getAction());
            }
        }
        actions.add("OPTIONS");
        return String.join(",", actions);
    }

    public Router setRoute(String path, String action, Handler handler) {
        Route route = new Route().withPath(path).withAction(action).withHandler(handler);
        List<Route> updatedRoutes = new ArrayList<>(this.routes);
        updatedRoutes.add(route);
        return new Router(updatedRoutes);
    }
}
