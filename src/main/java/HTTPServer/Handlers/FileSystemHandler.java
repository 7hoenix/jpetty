package HTTPServer.Handlers;

import HTTPServer.*;

import java.io.File;
import java.io.IOException;

public class FileSystemHandler implements Handler {
    private File root;
    private boolean autoIndex;

    public FileSystemHandler(File root, boolean autoIndex) {
        this.root = root;
        this.autoIndex = autoIndex;
    }

    public Response handle(Request request) throws IOException {
        if (FileHelper.findFile(root, request.getPath()).exists()) {
            return handleFileRequest(request);
        } else {
            return new Response(404);
        }
    }

    private Response handleFileRequest(Request request) throws IOException {
        if (FileHelper.findFile(root, request.getPath()).isDirectory()) {
            return handleDirectory(request);
        } else {
            return new FileHandler(root).handle(request);
        }
    }

    private Response handleDirectory(Request request) throws IOException {
        File index = FileHelper.findFile(root, request.getPath().concat("/index.html"));
        if (index.exists() && autoIndex) {
            Request updatedRequest = request.setPath(request.getPath().concat("/index.html"));
            return new FileHandler(root).handle(updatedRequest);
        } else {
            return generateDirectoryResponse(request);
        }
    }

    private Response generateDirectoryResponse(Request request) throws IOException {
        String body = "<!DOCTYPE html><html lang=\"en\"><body>";
        String links = generateLinks(request);
        byte[] fullBody = (body + links + "</body></html>").getBytes();
        return new Response(200).setHeader("Content-Type", "text/html").setBody(fullBody);
    }

    private String generateLinks(Request request) {
        String links = "";
        links = generateLinkUp(links, request);
        File[] filesInDir = FileHelper.findFile(root, request.getPath())
                .listFiles(pathname -> !pathname.isHidden());
        for (File file : filesInDir) {
            String path = request.getPath();
            if (path.equals("/"))
                path = "";
            path = path + "/" + file.getName();
            String resource = file.getName();
            links = links.concat(createLink(path, resource));
        }
        return links;
    }

    private String generateLinkUp(String links, Request request) {
        File currentFile = FileHelper.findFile(root, request.getPath());
        if ((currentFile.getParent() != null)) {
            links = links.concat(createLink("..", ".."));
        }
        return links;
    }

    private String createLink(String path, String resource) {
        return "<a href=\"" + path + "\">" + resource + "</a>\r\n";
    }
}
