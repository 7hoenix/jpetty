package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;

import java.io.File;
import java.io.IOException;

public class ETagHandler implements Handler {
    private Settings settings;
    private Repository dataStore;

    public ETagHandler(Settings settings, Repository dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        File currentFile = FileHelper.findFile(settings.getRoot(), request.getPath());
        String digest = FileHelper.findFileDigest(currentFile);
        return new Response(200)
                .setHeader("ETag", digest)
                .setBody(FileHelper.readFile(currentFile));
    }
}
