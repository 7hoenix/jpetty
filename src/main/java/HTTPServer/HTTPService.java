package HTTPServer;

import CobSpecApp.CobSpecRoutes;

import java.io.*;
import java.util.Map;

public class HTTPService  {
    private Setup settings;
    private DataStorage dataStore;

    public HTTPService(Setup settings) {
        this.settings = settings;
    }

    public HTTPService(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public HTTPService(String root) {
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = root;
        this.settings = new Setup(args);
    }

    public byte[] processInput(InputStream inputStream) throws IOException {
        Request request = new RequestFactory().create(inputStream);
        if (!request.isValid()) {
            return new ResponseFormatter().formatResponse(new Response(400));
        } else {
            Map routes = CobSpecRoutes.generate(settings, dataStore);
            Router router = new Router(routes);
            Handler handler = router.route(request);
            return new ResponseFormatter().formatResponse(handler.handle(request));
        }
    }
}
