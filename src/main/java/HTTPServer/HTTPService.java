package HTTPServer;

import CobSpecApp.CobSpecRoutes;

import java.io.*;
import java.util.Map;

public class HTTPService {
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

    public Response processInput(InputStream inputStream) throws IOException {
        Request request = new RequestFactory().create(inputStream);
        if (!request.isValid()) {
            byte[] header = "HTTP/1.1 400 BAD REQUEST\r\n".getBytes();
            return new ResponseFactory().create(header);
        } else {
            Map routes = CobSpecRoutes.generate(settings, dataStore);
            Router router = new Router(routes);
            Handler handler = router.route(request);
            return handler.handle(request);
        }
    }
}

