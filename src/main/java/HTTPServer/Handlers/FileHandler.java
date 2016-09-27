package HTTPServer.Handlers;

import HTTPServer.*;

import java.io.File;
import java.io.IOException;

public class FileHandler implements Handler {
    private File root;

    public FileHandler(File root) {
        this.root = root;
    }

    public Response handle(Request request) throws IOException {
        if (request.getAction().contains("GET")) {
            return handleGet(request);
        } else if (request.getAction().contains("PATCH")) {
            return handlePatch(request);
        } else {
            return new Response(405);
        }
    }

    private Response handlePatch(Request request) throws IOException {
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

    private Response handleGet(Request request) throws IOException {
        File currentFile = FileHelper.findFile(root, request.getPath());
        return new Response(200)
                .setHeader("Content-Type", FileHelper.findFileType(currentFile))
                .setHeader("Content-Length", new String(String.valueOf(FileHelper.findFileLength(currentFile))))
                .setBody(FileHelper.readFile(currentFile));
    }
}
