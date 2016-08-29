package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.Response;

import java.io.IOException;
import java.util.Map;

public interface Handler {
    Response handle(Request request) throws IOException;
}
