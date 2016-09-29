package HTTPServer.StaticFileHandlers;

import HTTPServer.FileHelper;
import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;

import java.io.File;
import java.io.IOException;

public class GetHandler implements Handler {
    private File root;

    public GetHandler(File root) {
        this.root = root;
    }

    @Override
    public Response handle(Request request) throws IOException {
        if (request.getHeader("Range") != null) {
            return new PartialContentHandler(root).handle(request);
        } else {
            File currentFile = FileHelper.findFile(root, request.getPath());
            return new Response(200)
                    .setHeader("Content-Type", FileHelper.findFileType(currentFile))
                    .setHeader("Content-Length", new String(String.valueOf(FileHelper.findFileLength(currentFile))))
                    .setHeader("ETag", FileHelper.findFileDigest(currentFile))
                    .setBody(FileHelper.readFile(currentFile));
        }
    }
}
