package HTTPServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jphoenix on 8/23/16.
 */
public class Router {
    Setup settings;

    public Router(Setup settings) {
        this.settings = settings;
    }

    public Response route(Map params) throws IOException {
        Handler handler = findRoute((String) params.get("action"));
        return handler.handle(params);
    }

    private Handler findRoute(String action) {
        HashMap<String, Handler> handlers = new HashMap();
        handlers.put("GET", new GetHandler(settings));
        handlers.put("HEAD", new HeadHandler(settings));
        handlers.put("OPTIONS", new OptionsHandler(settings));
        if (handlers.containsKey(action)) {
            return handlers.get(action);
        } else {
            return new ErrorHandler(settings);
        }
    }
}
