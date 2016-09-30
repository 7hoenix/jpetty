package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;

public class OptionsHandler implements Handler {
    public Response handle(Request request) {
        return new Response(200);
    }
}
