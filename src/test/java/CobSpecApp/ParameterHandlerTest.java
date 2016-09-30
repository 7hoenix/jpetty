package CobSpecApp;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public class ParameterHandlerTest extends TestCase {
    public void test_it_returns_the_parameters_in_the_body() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("variable_1", "cake");
        params.put("variable_2", "nom nom");
        Request request = new Request("/parameters", "GET").setParams(params);
        Handler handler = new ParameterHandler();

        Response response = handler.handle(request);

        assertEquals("variable_1 = cake\r\nvariable_2 = nom nom\r\n", new String(response.getBody(), "UTF-8"));
    }
}
