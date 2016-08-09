import HTTPServer.RequestParser;
import junit.framework.TestCase;

import java.util.Map;

/**
 * Created by jphoenix on 8/4/16.
 */
public class RequestParserTest extends TestCase {
    public void testItSeparatesLineIntoActionPathAndScheme() throws Exception {
        String request = "GET / HTTP/1.1\r\n\r\n";
        RequestParser parser = new RequestParser();

        Map requestMap = parser.parse(request);

        assertEquals(requestMap.get("line"), "GET / HTTP/1.1");
        assertEquals(requestMap.get("action"), "GET");
        assertEquals(requestMap.get("path"), "/");
        assertEquals(requestMap.get("scheme"), "HTTP/1.1");
    }
}
