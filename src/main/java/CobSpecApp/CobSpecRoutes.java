package CobSpecApp;

import HTTPServer.Setup;

import java.util.HashMap;
import java.util.Map;

public class CobSpecRoutes {
    public static Map generate(Setup settings) {
        HashMap<String, Handler> routes = new HashMap();
        routes.put("GET", new GetHandler(settings));
        routes.put("HEAD", new HeadHandler(settings));
        routes.put("OPTIONS", new OptionsHandler(settings));
        routes.put("POST", new PostHandler(settings));
        routes.put("PUT", new PutHandler(settings));
        return routes;
    }
}
