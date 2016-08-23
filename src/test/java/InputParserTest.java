import HTTPServer.InputParser;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jphoenix on 8/4/16.
 */
public class InputParserTest extends TestCase {
    public void testItSeparatesLineIntoActionPathAndScheme() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("GET / HTTP/1.1".getBytes());
        InputParser parser = new InputParser();

        Map parsedInput = parser.parse(inputStream);

        assertEquals(parsedInput.get("action"), "GET");
        assertEquals(parsedInput.get("path"), "/");
        assertEquals(parsedInput.get("scheme"), "HTTP/1.1");
    }

    public void test_it_can_handle_a_blank_input_stream() throws Exception {
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        InputParser parser = new InputParser();

        Map parsedInput = parser.parse(inputStream);

        assert(parsedInput.isEmpty());
    }
}
