package HTTPServer;

import java.io.*;
import java.util.Map;

/**
 * Created by jphoenix on 8/4/16.
 */
public class HTTPService {
    private Setup settings;

    public HTTPService(Setup settings) {
        this.settings = settings;
    }

    public HTTPService(String root) {
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = root;
        this.settings = new Setup(args);
    }

    public byte[] processInput(InputStream inputStream) throws IOException {
        InputParser parser = new InputParser();
        Map parsedRequest = parser.parse(inputStream);
        if (parsedRequest.isEmpty()) {
            return "HTTP/1.1 400 BAD REQUEST\r\n\r\n".getBytes();
        } else {
            Router router = new Router(settings);
            return router.route(parsedRequest);
        }
    }
}