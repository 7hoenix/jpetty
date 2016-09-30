package HTTPServer;

import java.io.Closeable;
import java.io.IOException;

public interface ConnectionManager extends Closeable {
    Connection acceptConnection() throws IOException;
    boolean isListening();
}
