package HTTPServer;

import HTTPServer.GetHandler;
import HTTPServer.Handler;
import HTTPServer.HeadHandler;
import HTTPServer.Setup;

import java.io.IOException;
import java.util.Map;

/**
 * Created by jphoenix on 8/23/16.
 */
public class Router {
    Setup settings;

    public Router(Setup settings) {
        this.settings = settings;
    }

    public byte[] route(Map params) throws IOException {
        if (params.get("action").equals("GET")) {
            return new GetHandler(settings).handle(params);
        } else if (params.get("action").equals("HEAD")) {
            return new HeadHandler(settings).handle(params);
        } else {
            return "HTTP/1.1 400 BAD REQUEST\r\n\r\n".getBytes();
        }
    }
}
