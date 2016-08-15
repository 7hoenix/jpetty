import HTTPServer.RequestParser;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by jphoenix on 8/4/16.
 */
public class RequestParserTest extends TestCase {
    public void testItSeparatesLineIntoActionPathAndScheme() throws Exception {
        InputStream request = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());
        RequestParser parser = new RequestParser(request);

        parser.parse();

        assertEquals(parser.getParams().get("line"), "GET / HTTP/1.1");
        assertEquals(parser.getParams().get("action"), "GET");
        assertEquals(parser.getParams().get("path"), "/");
        assertEquals(parser.getParams().get("scheme"), "HTTP/1.1");
        assertEquals(parser.getIn(), request);
    }
}
