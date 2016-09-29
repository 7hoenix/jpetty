package CobSpecApp;

import HTTPServer.Repository;
import HTTPServer.Router;
import HTTPServer.Handler;

import java.util.HashMap;
import java.util.Map;

public class CobSpecRoutes {
    public static Handler generate(Router router, Repository dataStore) {
        Handler updatedRouter = router
                .setRoute("/form", "GET", new FormHandler(dataStore))
                .setRoute("/form", "PUT", new FormHandler(dataStore))
                .setRoute("/form", "POST", new FormHandler(dataStore))
                .setRoute("/form", "DELETE", new FormHandler(dataStore))
                .setRoute("/method_options", "GET", new OptionsHandler())
                .setRoute("/method_options", "HEAD", new OptionsHandler())
                .setRoute("/method_options", "POST", new OptionsHandler())
                .setRoute("/method_options", "PUT", new OptionsHandler())
                .setRoute("/method_options2", "GET", new OptionsHandler())
                .setRoute("/redirect", "GET", new RedirectHandler(getRedirects()))
                .setRoute("/coffee", "GET", new TeapotHandler())
                .setRoute("/tea", "GET", new TeapotHandler())
                .setRoute("/parameters", "GET", new ParameterHandler())
                .setRoute("/.*", "GET", new WildCardHandler());
        return updatedRouter;
    }

    private static Map<String, String> getRedirects() {
        Map redirections = new HashMap<String, String>();
        redirections.put("/redirect", "http://localhost:5000/");
        return redirections;
    }
}
