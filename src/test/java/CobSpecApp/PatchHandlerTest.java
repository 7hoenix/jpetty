package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;
import junit.framework.TestCase;

public class PatchHandlerTest extends TestCase {
    public void test_it_can_patch_data() throws Exception {
        Request request = new Request("PATCH", "/patch-content.txt");
        Handler handler = new PatchHandler(new Settings(), new DataStore());

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("default content", new String(response.getBody(), "UTF-8"));
    }

    public void test_it_can_update_the_file_contents_with_a_given_shaw() throws Exception {
        Request requestPatch1 = new Request("PATCH", "/patch-content.txt")
                .setHeader("If-Match", "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec");
        Handler handler = new PatchHandler(new Settings(), new DataStore());
        Response response = handler.handle(requestPatch1);
        assertEquals(204, response.getStatusCode());

        Request requestGet1 = new Request("GET", "/patch-content.txt");
        Response getResponse = handler.handle(requestGet1);
        assertEquals("patched content", new String(getResponse.getBody(), "UTF-8"));

        Request requestPatch2 = new Request("PATCH", "/patch-content.txt")
                .setHeader("If-Match", "5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0");
        Response responsePatch2 = handler.handle(requestPatch2);
        assertEquals(204, responsePatch2.getStatusCode());

        Request requestGet2 = new Request("GET", "/patch-content.txt");
        Response responseGet2 = handler.handle(requestGet2);
        assertEquals("default content", new String(responseGet2.getBody(), "UTF-8"));
    }
}
