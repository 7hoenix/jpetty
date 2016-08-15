package HTTPServer;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jphoenix on 8/1/16.
 */
public interface Connectible extends Closeable {
    InputStream getInputStream() throws IOException;
    void write(byte[] response) throws IOException;
    void close();
}
