package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;

public class OptionsHandler implements Handler {
    public Response handle(Request request) {
        return new Response(200);
    }
}
