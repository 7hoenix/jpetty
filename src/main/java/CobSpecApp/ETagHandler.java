package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;

import java.io.File;
import java.io.IOException;

public class ETagHandler implements Handler {
    private File root;

    public ETagHandler(File root) {
        this.root = root;
    }

    public Response handle(Request request) throws IOException {
        File currentFile = FileHelper.findFile(root, request.getPath());
        String digest = FileHelper.findFileDigest(currentFile);
        return new Response(200)
                .setHeader("ETag", digest)
                .setBody(FileHelper.readFile(currentFile));
    }
}
