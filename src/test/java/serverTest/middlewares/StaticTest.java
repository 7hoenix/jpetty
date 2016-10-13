package serverTest.middlewares;

import server.BasicHandler;
import server.Handler;
import server.Middleware;
import server.Request;
import server.Response;
import junit.framework.TestCase;
import server.middlewares.WrapServeStaticFiles;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StaticTest extends TestCase {
    public void test_it_can_handle_a_static_file() throws Exception {
        File tempDir = new File(String.valueOf(Files.createTempDirectory("file")));
        File tempFile = File.createTempFile("file", ".txt", tempDir);
        Path tempPath = Paths.get(tempFile.getAbsolutePath());
        Files.write(tempPath, "Important Content".getBytes());
        Request request = new Request("/" + tempPath.getFileName(), "GET");
        Middleware staticFileMiddleware = new WrapServeStaticFiles()
                .withPublicDirectory(tempDir);
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("Important Content", new String(response.getBody()));
    }

    public void test_it_renders_a_directory_structure_appropiately() throws Exception {
        File tempDir = new File(String.valueOf(Files.createTempDirectory("directory")));
        File tempFile = File.createTempFile("file", ".txt", tempDir);
        File tempFile2 = File.createTempFile("file", ".jpeg", tempDir);
        Request request = new Request("/", "GET");
        Middleware staticFileMiddleware = new WrapServeStaticFiles()
                .withPublicDirectory(tempDir)
                .withAutoIndex(true);
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response response = handler.handle(request);

        assertEquals(200, response.getStatusCode());
        assert(new String(response.getBody()).contains(createLink("/" + tempFile.getName(), tempFile.getName())));
        assert(new String(response.getBody()).contains(createLink("/" + tempFile2.getName(), tempFile2.getName())));
    };

    public void test_it_handles_method_not_allowed_differently_for_different_file_types() throws Exception {
        Request getRequest = new Request("/thing.txt", "GET");
        Request putRequest = new Request("/thing.txt", "PUT");
        Request purgeRequest = new Request("/thing.txt", "PURGE");
        Middleware staticFileMiddleware = new WrapServeStaticFiles()
                .withPublicDirectory(new File("otherPublic"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response getResponse = handler.handle(getRequest);
        Response putResponse = handler.handle(putRequest);
        Response purgeResponse = handler.handle(purgeRequest);

        assertEquals(200, getResponse.getStatusCode());
        assertEquals(405, putResponse.getStatusCode());
        assertEquals(405, purgeResponse.getStatusCode());
    }

    public void test_it_changes_the_content_if_passed_a_sha_that_matches_the_current_file_contents() throws Exception {
        Request getRequest = new Request("/patch-content.txt", "GET");
        Middleware staticFileMiddleware = new WrapServeStaticFiles()
                .withPublicDirectory(new File("public"));
        Handler handler = staticFileMiddleware.apply(new BasicHandler());

        Response getResponse1 = handler.handle(getRequest);

        assertEquals("default content\r", new String(getResponse1.getBody(), "UTF-8"));

        String tag1 = getResponse1.getHeader("ETag");
        String body1 = new String(getResponse1.getBody(), "UTF-8");

        Request patchRequest1 = new Request("/patch-content.txt", "PATCH")
                .setHeader("If-Match", tag1)
                .setBody("patched content");

        Response patchResponse1 = handler.handle(patchRequest1);
        Response getResponse2 = handler.handle(getRequest);

        assertEquals(204, patchResponse1.getStatusCode());
        assertEquals("patched content\r", new String(getResponse2.getBody(), "UTF-8"));

        String tag2 = getResponse2.getHeader("ETag");
        String body2 = new String(getResponse2.getBody(), "UTF-8");

        Request patchRequest2 = new Request("/patch-content.txt", "PATCH")
                .setHeader("If-Match", tag2)
                .setBody("default content");

        Response patchResponse2 = handler.handle(patchRequest2);
        Response getResponse3 = handler.handle(getRequest);

        assertEquals(204, patchResponse2.getStatusCode());
        assertEquals("default content\r", new String(getResponse3.getBody(), "UTF-8"));

        String tag3 = getResponse3.getHeader("ETag");
        String body3 = new String(getResponse3.getBody(), "UTF-8");

        assertEquals(body1, body3);
        assertNotSame(body2, body3);
        assertEquals(tag3, tag1);
    }

    private String createLink(String path, String resource) {
        return "<a href=\"" + path + "\">" + resource + "</a>\r\n";
    }
}
