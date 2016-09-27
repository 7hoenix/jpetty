package HTTPServer.Handlers;

import HTTPServer.*;

import java.io.*;
import java.util.ArrayList;

public class BasicHandler implements Handler {
    private ArrayList<String> log;
    private boolean autoIndex;

    public BasicHandler(ArrayList<String> log, boolean autoIndex) {
        this.log = log;
        this.autoIndex = autoIndex;
    }

    public Response handle(Request request) throws IOException {
        if (request != null && request.getPath().contains("/logs")) {
            return new AuthorizationHandler(log).handle(request);
        } else if (request != null) {
            return new FileSystemHandler(new File("public"), autoIndex).handle(request);
        } else {
            return new Response(400);
        }
    }
}
