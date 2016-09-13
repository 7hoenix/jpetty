package Server.Handlers;

import CobSpecApp.ETagHandler;
import HTTPServer.*;
import HTTPServer.Handlers.FileHandler;
import HTTPServer.Handlers.Handler;
import junit.framework.TestCase;

public class FileHandlerTest extends TestCase {
    public void test_it_handles_method_not_allowed_differently_for_different_file_types() throws Exception {
        Request getRequest = new Request("/thing.txt", "GET");
        Request putRequest = new Request("/thing.txt", "PUT");
        Request purgeRequest = new Request("/thing.txt", "PURGE");
        Handler handler = new FileHandler(new Settings(new String[] {"-d", "otherPublic"}), new Router());

        Response getResponse = handler.handle(getRequest);
        Response putResponse = handler.handle(putRequest);
        Response purgeResponse = handler.handle(purgeRequest);

        assertEquals(200, getResponse.getStatusCode());
        assertEquals(405, putResponse.getStatusCode());
        assertEquals(405, purgeResponse.getStatusCode());
    }

    public void test_it_changes_the_content_if_passed_a_sha_that_matches_the_current_file_contents() throws Exception {
        Request getRequest = new Request("/patch-content.txt", "GET");
        Handler getHandler = new ETagHandler(new Settings(), new DataStore());
        Handler handler = new FileHandler(new Settings(), new Router());

        Response getResponse1 = getHandler.handle(getRequest);

        assertEquals("default content\r", new String(getResponse1.getBody(), "UTF-8"));

        String tag1 = getResponse1.getHeader("ETag");
        String body1 = new String(getResponse1.getBody(), "UTF-8");

        Request patchRequest1 = new Request("/patch-content.txt", "PATCH")
                .setHeader("If-Match", tag1)
                .setBody("patched content");

        Response patchResponse1 = handler.handle(patchRequest1);
        Response getResponse2 = getHandler.handle(getRequest);

        assertEquals(204, patchResponse1.getStatusCode());
        assertEquals("patched content\r", new String(getResponse2.getBody(), "UTF-8"));

        String tag2 = getResponse2.getHeader("ETag");
        String body2 = new String(getResponse2.getBody(), "UTF-8");

        Request patchRequest2 = new Request("/patch-content.txt", "PATCH")
                .setHeader("If-Match", tag2)
                .setBody("default content");

        Response patchResponse2 = handler.handle(patchRequest2);
        Response getResponse3 = getHandler.handle(getRequest);

        assertEquals(204, patchResponse2.getStatusCode());
        assertEquals("default content\r", new String(getResponse3.getBody(), "UTF-8"));

        String tag3 = getResponse3.getHeader("ETag");
        String body3 = new String(getResponse3.getBody(), "UTF-8");

        assertEquals(body1, body3);
        assertNotSame(body2, body3);
        assertEquals(tag3, tag1);
    }
}
