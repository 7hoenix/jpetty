package HTTPServer;

import java.util.Map;

public class PostHandler implements Handler {
    Setup settings;

    public PostHandler(Setup settings) {
        this.settings = settings;
    }

    public Response handle(Map params) {
        Response response = new Response();
        response.setHeader("HTTP/1.1 200 OK\r\n".getBytes());
        return response;
    }
}
