package HTTPServer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by jphoenix on 8/23/16.
 */
public interface Handler {
    byte[] handle(Map request) throws IOException;
}
