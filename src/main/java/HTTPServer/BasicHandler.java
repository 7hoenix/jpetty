package HTTPServer;

import CobSpecApp.CobSpecRoutes;

import java.io.*;
import java.util.Map;

public class BasicHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public BasicHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public BasicHandler(String root) {
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = root;
        this.settings = new Setup(args);
        this.dataStore = new DataStore();
    }

    public Response handle(Request request) throws IOException {
        if (request != null) {
            Map routes = CobSpecRoutes.generate(settings, dataStore);
            Router router = new Router(routes);
            Handler handler = router.route(request);
            return handler.handle(request);
        } else {
            return new Response(400);
        }
    }
}
