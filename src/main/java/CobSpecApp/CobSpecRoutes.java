package CobSpecApp;

import HTTPServer.Router;

public class CobSpecRoutes {
    public static Router generate(Router router) {
        Router updatedRouter = router
                .add("/form", new String[] {"GET", "PUT", "POST", "DELETE"}, new FormHandler(router.getSettings(), router.getDataStore()))
                .add("/method_options", new String[] {"GET", "HEAD", "POST", "PUT"}, new OptionsHandler(router.getSettings(), router.getDataStore()))
                .add("/method_options2", new String[] {"GET"}, new OptionsHandler(router.getSettings(), router.getDataStore()))
                .add("/redirect", "GET", new RedirectHandler(router.getSettings(), router.getDataStore()))
                .add("/partial_content.txt", "GET", new PartialContentHandler(router.getSettings(), router.getDataStore()))
                .add("/coffee", "GET", new TeapotHandler(router.getSettings(), router.getDataStore()))
                .add("/tea", "GET", new TeapotHandler(router.getSettings(), router.getDataStore()))
                .add("/parameters", "GET", new ParameterHandler(router.getSettings(), router.getDataStore()))
                .add("/patch-content.txt", "GET", new ETagHandler(router.getSettings(), router.getDataStore()));
        return updatedRouter;
    }
}


