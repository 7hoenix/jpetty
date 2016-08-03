package HTTPServer;

import java.io.IOException;

/**
 * Created by jphoenix on 8/1/16.
 */
public interface ServerConnectable {
    Connectable listenForRequest() throws IOException;
}
