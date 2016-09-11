package HTTPServer.Handlers;

import HTTPServer.Request;
import HTTPServer.Response;

import java.io.IOException;

public interface Handler {
    Response handle(Request request) throws IOException;
}
