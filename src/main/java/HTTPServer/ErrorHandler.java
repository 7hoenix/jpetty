package HTTPServer;

import java.util.Map;

/**
 * Created by jphoenix on 8/24/16.
 */
public class ErrorHandler implements Handler{
    private Setup settings;

    public ErrorHandler(Setup settings) {
        this.settings = settings;
    }

    public Response handle(Map params) {
        Response response = new Response();
        response.setHeader("HTTP/1.1 404 NOT FOUND\r\n".getBytes());
        return response;
    }
}
