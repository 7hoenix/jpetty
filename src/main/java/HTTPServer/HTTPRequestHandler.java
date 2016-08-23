package HTTPServer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jphoenix on 8/4/16.
 */
public class HTTPRequestHandler {
    private Setup settings;

    public HTTPRequestHandler(Setup settings) {
        this.settings = settings;
    }

    public HTTPRequestHandler(String root) {
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = root;
        this.settings = new Setup(args);
    }

    public byte[] handle(InputStream request) throws IOException {
        RequestParser parser = new RequestParser(request);
        parser.parse();
        Map parsedRequest = parser.getParams();
        if (parsedRequest.isEmpty()) {
            return "HTTP/1.1 400 BAD REQUEST\r\n\r\n".getBytes();
        } else {
            Router router = new Router(settings);
            return router.route(parsedRequest);
        }
    }
}