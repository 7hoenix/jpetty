package CobSpecApp;

import HTTPServer.Response;

import java.io.IOException;
import java.util.Map;

public interface Handler {
    Response handle(Map request) throws IOException;
}
