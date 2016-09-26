package CobSpecApp;

import HTTPServer.Router;

public class CobSpecRoutes {
    public static Router generate(Router router, Repository dataStore) {
        Router updatedRouter = router
                .add("/form", new String[] {"GET", "PUT", "POST", "DELETE"}, new FormHandler(router.getSettings(), dataStore))
                .add("/method_options", new String[] {"GET", "HEAD", "POST", "PUT"}, new OptionsHandler(router.getSettings(), dataStore))
                .add("/method_options2", new String[] {"GET"}, new OptionsHandler(router.getSettings(), dataStore))
                .add("/redirect", "GET", new RedirectHandler(router.getSettings(), dataStore))
                .add("/partial_content.txt", "GET", new PartialContentHandler(router.getSettings(), dataStore))
                .add("/coffee", "GET", new TeapotHandler(router.getSettings(), dataStore))
                .add("/tea", "GET", new TeapotHandler(router.getSettings(), dataStore))
                .add("/parameters", "GET", new ParameterHandler(router.getSettings(), dataStore))
                .add("/patch-content.txt", "GET", new ETagHandler(router.getSettings(), dataStore));
        return updatedRouter;
    }
}


