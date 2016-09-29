package HTTPServer;

import java.io.*;

public class BasicHandler implements Handler {
    public Response handle(Request request) throws IOException {
        return new Response(404);
    }
}
