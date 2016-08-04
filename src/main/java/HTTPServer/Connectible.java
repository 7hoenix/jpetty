package HTTPServer;

import java.io.IOException;

/**
 * Created by jphoenix on 8/1/16.
 */
public interface Connectible {
    String read() throws IOException;
    void write(String response);
    void close();
}
