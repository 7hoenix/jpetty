package HTTPServer.Handlers;

import HTTPServer.*;

import java.io.*;
import java.util.ArrayList;

public class BasicHandler implements Handler {
    private Settings settings;
    private Router router;
    private ArrayList<String> log;

    public BasicHandler(Settings settings, Router router, ArrayList<String> log) {
        this.settings = settings;
        this.router = router;
        this.log = log;
    }

    public Response handle(Request request) throws IOException {
        if (request != null && request.getPath().contains("/logs")) {
            return new AuthorizationHandler(settings, log).handle(request);
        } else if (request != null) {
            return new FileSystemHandler(settings, router).handle(request);
        } else {
            return new Response(400);
        }
    }
}
