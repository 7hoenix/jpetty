package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Parsers.RequestParser;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;

public class FormHandlerTest extends TestCase {
    public void test_it_returns_an_html_form_when_form_is_get() throws Exception {
        Request request = new RequestParser().parse(new ByteArrayInputStream("GET /form HTTP/1.1\r\n\r\n".getBytes()));
        FormHandler handler = new FormHandler(new DataStore());

        Response response = handler.handle(request);

        String expectedResponse = "<!DOCTYPE html><html lang=\"en\"><body>" + htmlForm() + "</body></html>";
        assertEquals(expectedResponse, new String(response.getBody(), "UTF-8"));
    }

    public void test_it_changes_the_given_data_store_if_given_a_post_request() throws Exception {
        Repository dataStore = new DataStore();
        FormHandler handler = new FormHandler(dataStore);
        Request request = new RequestParser().parse(new ByteArrayInputStream(("POST /form HTTP/1.1\r\n\r\n" +
                "data=fat%20cat").getBytes()));

        handler.handle(request);

        assertEquals(dataStore.retrieve("data"), "fat cat");
    }

    public void test_it_renders_the_form_with_the_stored_data_if_present() throws Exception {
        Repository dataStore = new DataStore();
        dataStore.store("data", "cake yo");
        Request request = new RequestParser().parse(new ByteArrayInputStream("GET /form HTTP/1.1\r\n\r\n".getBytes()));
        FormHandler handler = new FormHandler(dataStore);

        Response response = handler.handle(request);

        String expectedResponse = "<!DOCTYPE html><html lang=\"en\"><body>" + htmlForm() + "data=cake yo</body></html>";
        assertEquals(expectedResponse, new String(response.getBody(), "UTF-8"));
    }

    public void test_it_changes_the_form_data_if_passed_a_put_request() throws Exception {
        Repository dataStore = new DataStore();
        dataStore.store("data", "cake yo");
        Request request = new Request("/form", "PUT").setParam("data", "pie yo");
        FormHandler handler = new FormHandler(dataStore);

        handler.handle(request);

        assertEquals(dataStore.retrieve("data"), "pie yo");
    }

    public void test_it_removes_the_data_store_data_if_remove_is_called() throws Exception {
        Repository dataStore = new DataStore();
        dataStore.store("data", "cake yo");
        Request request = new Request("/form", "DELETE");
        FormHandler handler = new FormHandler(dataStore);

        handler.handle(request);

        assertEquals(dataStore.retrieve("data"), "");
    }

    private String htmlForm() {
        return "<form action=\"/form\" method=\"post\">" +
                "<input name=\"data\" type=\"text\">" +
                "<input type=\"submit\" value=\"submit\">" +
                "</form>";
    }
}
