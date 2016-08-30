package CobSpecApp;

import HTTPServer.Request;
import HTTPServer.RequestFactory;
import HTTPServer.Response;
import HTTPServer.Setup;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;

public class FormHandlerTest extends TestCase {
    public void test_it_returns_an_html_form_when_form_is_get() throws Exception {
        Request request = new RequestFactory().create(new ByteArrayInputStream("GET /form HTTP/1.1\r\n\r\n".getBytes()));
        String formData = "<form action=\"/form\" method=\"post\">" +
                "<input name=\"say\">" +
                "<input name=\"to\">" +
                "<button>Send my greetings</button>" +
                "</form>";
        FormHandler handler = new FormHandler(new Setup(new String[0]));

        Response response = handler.handle(request);

        assertEquals("<!DOCTYPE html><html lang=\"en\"><body>" + formData +
                "</body></html>", new String(response.getBody(), "UTF-8"));
    }
}
