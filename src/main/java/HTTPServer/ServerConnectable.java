package HTTPServer;

import java.io.Closeable;
import java.io.IOException;

public interface ServerConnectable extends Closeable {
    Connection accept() throws IOException;
    boolean isListening();
}
