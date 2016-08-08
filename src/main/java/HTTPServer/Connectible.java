package HTTPServer;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by jphoenix on 8/1/16.
 */
public interface Connectible extends Closeable {
    String read() throws IOException;
    void write(String response) throws IOException;
    void close();
}
