package HTTPServer;

import java.io.IOException;

public interface Handler {
    Response handle(Request request) throws IOException;
}
