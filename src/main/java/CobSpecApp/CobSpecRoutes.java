package CobSpecApp;

import HTTPServer.Router;

import java.util.HashMap;
import java.util.Map;

public class CobSpecRoutes {
    public static Router generate(Router router, Settings settings, Repository dataStore) {
        Router updatedRouter = router
                .add("/form", new String[] {"GET", "PUT", "POST", "DELETE"}, new FormHandler(dataStore))
                .add("/method_options", new String[] {"GET", "HEAD", "POST", "PUT"}, new OptionsHandler())
                .add("/method_options2", new String[] {"GET"}, new OptionsHandler())
                .add("/redirect", "GET", new RedirectHandler(getRedirects()))
                .add("/partial_content.txt", "GET", new PartialContentHandler(settings.getRoot()))
                .add("/coffee", "GET", new TeapotHandler())
                .add("/tea", "GET", new TeapotHandler())
                .add("/parameters", "GET", new ParameterHandler())
                .add("/patch-content.txt", "GET", new ETagHandler(settings.getRoot()));
        return updatedRouter;
    }

    private static Map<String, String> getRedirects() {
        Map redirections = new HashMap<String, String>();
        redirections.put("/redirect", "http://localhost:5000/");
        return redirections;
    }
}
