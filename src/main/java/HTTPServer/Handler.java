package HTTPServer;

import java.io.IOException;
import java.util.Map;

/**
 * Created by jphoenix on 8/23/16.
 */
public interface Handler {
    Response handle(Map request) throws IOException;
}
