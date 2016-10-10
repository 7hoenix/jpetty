package serverTest.parsers;

import junit.framework.TestCase;
import server.parsers.RequestBodyParser;

public class RequestBodyParserTest extends TestCase {
    public void test_it_can_find_the_body() throws Exception {
        byte[] rawRequest = ("GET / HTTP/1.1\r\n\r\nFIND ME").getBytes();

        assertEquals("FIND ME", new RequestBodyParser().parse(rawRequest));
    }
}
