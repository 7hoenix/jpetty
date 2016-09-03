package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.RequestFactory;
import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;

public class OptionsHandlerTest extends TestCase {
    public void test_it_returns_a_listing_of_the_methods_that_will_work_on_a_resource() throws Exception {
        Request request = new RequestFactory().create(new ByteArrayInputStream("OPTIONS /method_options HTTP/1.1\r\n\r\n".getBytes()));
        OptionsHandler handler = new OptionsHandler(new Setup(new String[0]));

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("GET,HEAD,POST,OPTIONS,PUT", response.getHeader("Allow"));
    }

    public void test_it_returns_different_results_if_a_file_is_not_there() throws Exception {
        Request request = new RequestFactory().create(new ByteArrayInputStream("OPTIONS /method_options2 HTTP/1.1\r\n\r\n".getBytes()));
        OptionsHandler handler = new OptionsHandler(new Setup(new String[0]));

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("GET,OPTIONS,PUT", response.getHeader("Allow"));
    }
}
