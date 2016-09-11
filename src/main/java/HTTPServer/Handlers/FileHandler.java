package HTTPServer.Handlers;

import HTTPServer.*;

import java.io.File;
import java.io.IOException;

public class FileHandler implements Handler {
    private Settings settings;
    private Router router;

    public FileHandler(Settings settings, Router router) {
        this.settings = settings;
        this.router = router;
    }

    public Response handle(Request request) throws IOException {
        if (request.getAction().contains("GET")) {
            return handleGet(request);
        } else {
            return new Response(405);
        }
    }

    private Response handleGet(Request request) throws IOException {
        File currentFile = FileHelper.findFile(settings.getRoot(), request.getPath());
        return new Response(200)
                .setHeader("Content-Type", FileHelper.findFileType(currentFile))
                .setHeader("Content-Length", new String(String.valueOf(FileHelper.findFileLength(currentFile))))
                .setBody(FileHelper.readFile(currentFile));
    }
}
