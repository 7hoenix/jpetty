package HTTPServer;

import java.util.Map;

/**
 * Created by jphoenix on 8/24/16.
 */
public class ErrorHandler implements Handler{
    private String message;

    public ErrorHandler(String message) {
        this.message = message;
    }

    public Response handle(Map params) {
        Response response = new Response();
        response.setHeader(message.getBytes());
        return response;
    }
}
