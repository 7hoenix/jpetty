import HTTPServer.RequestParser;
import junit.framework.TestCase;

import java.util.Map;

/**
 * Created by jphoenix on 8/4/16.
 */
public class RequestParserTest extends TestCase {
    public void testItSeparatesLineHeadersAndBody() throws Exception {
        String request = "GET / HTTP/1.1\rContent-Type: text/html\r\r<htm><body>cake</body></html>";
        RequestParser parser = new RequestParser();

        Map requestMap = parser.parse(request);

        assertEquals(requestMap.get("line"), "GET / HTTP/1.1");
        assertEquals(requestMap.get("headers"), "Content-Type: text/html");
        assertEquals(requestMap.get("body"), "<htm><body>cake</body></html>");
    }
}
