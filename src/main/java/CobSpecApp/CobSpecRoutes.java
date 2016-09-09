package CobSpecApp;

import HTTPServer.DataStorage;
import HTTPServer.Router2;
import HTTPServer.Settings;

public class CobSpecRoutes {
    public static Router2 generate(Router2 router, Settings settings, DataStorage dataStore) {
        Router2 updatedRouter = router
                .add("/form", new String[] {"GET", "PUT", "POST", "DELETE"}, new FormHandler(settings, dataStore))
                .add("/method_options", new String[] {"GET", "HEAD", "POST", "OPTIONS", "PUT"}, new FormHandler(settings, dataStore))
                .add("/method_options2", new String[] {"GET", "OPTIONS"}, new FormHandler(settings, dataStore))
                .add("/redirect", "GET", new GetHandler(settings, dataStore))
                .add("/partial_content.txt", "GET", new PartialContentHandler(settings, dataStore))
                .add("/coffee", "GET", new TeapotHandler(settings, dataStore))
                .add("/tea", "GET", new TeapotHandler(settings, dataStore))
//                .add("/parameters", "GET", new GetHandler(settings, dataStore));
                .add("/logs", "GET", new AuthorizationHandler(settings, dataStore));
//                .add("/file1", new String[] {"GET", "PUT", "PURGE"}, new MethodMissingHandler(settings, dataStore))
//                .add("/text-file.txt", new String[] {"GET", "POST"}, new MethodMissingHandler(settings, dataStore))
//                .add("/patch-content.txt", new String[] {"GET", "PATCH"}, new PatchHandler(settings, dataStore))
//                .add("/image.jpeg", "GET", new GetHandler(settings, dataStore))
//                .add("/image.png", "GET", new GetHandler(settings, dataStore))
//                .add("/image.gif", "GET", new GetHandler(settings, dataStore));
//                .add("^/.*", new String[] {"GET", "HEAD"}, new GetHandler(settings, dataStore));
        return updatedRouter;
//                .add("GET", new GetHandler(settings, dataStore))
//                .add("HEAD", new HeadHandler(settings, dataStore))
//                .add("OPTIONS", new OptionsHandler(settings, dataStore))
//                .add("POST", new PostHandler(settings, dataStore))
//                .add("PUT", new PutHandler(settings, dataStore));
    }
}


