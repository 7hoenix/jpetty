package HTTPServer;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jphoenix on 8/1/16.
 */
public interface Connectable {
    public InputStream getRequest();
    public OutputStream giveResponse();
}
