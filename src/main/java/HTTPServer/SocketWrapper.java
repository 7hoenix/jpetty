package HTTPServer;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jphoenix on 8/1/16.
 */
public class SocketWrapper implements Connectable {

    @Override
    public InputStream getRequest() {
        return null;
    }

    @Override
    public OutputStream giveResponse() {
        return null;
    }
}
