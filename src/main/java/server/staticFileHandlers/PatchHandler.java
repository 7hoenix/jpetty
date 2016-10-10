package server.staticFileHandlers;

import server.FileHelper;
import server.Handler;
import server.Request;
import server.Response;

import java.io.File;
import java.io.IOException;

public class PatchHandler implements Handler {
    private File root;

    public PatchHandler(File root) {
        this.root = root;
    }

    @Override
    public Response handle(Request request) throws IOException {
        String eTag = request.getHeader("If-Match");
        File currentFile = FileHelper.findFile(root, request.getPath());
        String currentDigest = FileHelper.findFileDigest(currentFile);
        if (eTag.toUpperCase().equals(currentDigest)) {
            FileHelper.changeFile(currentFile, request.getBody());
            return new Response(204);
        } else {
            return new Response(400);
        }
    }
}
