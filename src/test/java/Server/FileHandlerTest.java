package Server;

import HTTPServer.*;
import junit.framework.TestCase;

public class FileHandlerTest extends TestCase {
    public void test_it_returns_the_contents_of_a_file() throws Exception {
        Request request = new Request("/thingy.html", "GET");
        Handler handler = new FileHandler(new Settings(new String[] {"-d", "otherPublic"}));

        Response response = handler.handle(request);

        String expectedBody = "<!DOCTYPE html><html lang=\"en\"><body><h1>Thingy has a point!</h1></body></html>";
        assertEquals(200, response.getStatusCode());
        assertEquals(expectedBody, response.getBody());
    }
}
