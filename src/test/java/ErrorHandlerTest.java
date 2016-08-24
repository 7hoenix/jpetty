import HTTPServer.ErrorHandler;
import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.util.HashMap;

/**
 * Created by jphoenix on 8/24/16.
 */
public class ErrorHandlerTest extends TestCase {
    public void test_it_returns_an_error_response() throws Exception {
        ErrorHandler handler = new ErrorHandler(new Setup(new String[0]));

        Response response = handler.handle(new HashMap());

        assertEquals("HTTP/1.1 404 NOT FOUND\r\n\r\n", new String(response.getFull(), "UTF-8"));
    }
}
