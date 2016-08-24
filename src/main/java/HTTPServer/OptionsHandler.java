package HTTPServer;

import java.io.File;
import java.util.Map;

/**
 * Created by jphoenix on 8/24/16.
 */
public class OptionsHandler implements Handler {
    Setup settings;

    public OptionsHandler(Setup settings) {
        this.settings = settings;
    }

    public Response handle(Map params) {
        Response response = new Response();
        if (params.get("path").equals("/method_options")) {
            response.setHeader("HTTP/1.1 200 OK\r\nAllow: GET,HEAD,POST,OPTIONS,PUT\r\n".getBytes());
        } else {
            response.setHeader("HTTP/1.1 200 OK\r\nAllow: GET,OPTIONS\r\n".getBytes());
        }
        return response;
    }
}
