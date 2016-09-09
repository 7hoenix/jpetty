package HTTPServer;

import java.io.*;

public class BasicHandler implements Handler {
    private Settings settings;
    private DataStorage dataStore;
    private Router2 router;

    public BasicHandler(Settings settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public BasicHandler(Router2 router) {
        this.router = router;
    }

    public BasicHandler(String root) {
        this.settings = new Settings(new String[] {"-d", root});
        this.dataStore = new DataStore();
    }

    public Response handle(Request request) throws IOException {
        if (request != null) {
            return router.route(request);
        } else {
            return new Response(400);
        }
    }
}
