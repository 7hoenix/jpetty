package HTTPServer;

import java.io.Closeable;
import java.io.IOException;

public interface ServerConnectible extends Closeable {
    Connectible accept() throws IOException;
    Boolean listening();
    void close();
}
