package HTTPServer.Handlers;

import HTTPServer.*;

import java.io.File;
import java.io.IOException;

public class FileSystemHandler implements Handler {
    private Settings settings;
    private Router router;

    public FileSystemHandler(Settings settings, Router router) {
        this.settings = settings;
        this.router = router;
    }

    public Response handle(Request request) throws IOException {
        if (request.getAction().contains("OPTIONS")) {
            return handleOptionsRequest(request);
        } else if (FileHelper.findFile(settings.getRoot(), request.getPath()).exists()) {
            return handleFileRequest(request);
        } else {
            return new Response(404);
        }
    }

    private Response handleOptionsRequest(Request request) {
        return new Response(200)
                .setHeader("Allow", router.getValidActions(request.getPath()));
    }

    private Response handleFileRequest(Request request) throws IOException {
        if (FileHelper.findFile(settings.getRoot(), request.getPath()).isDirectory()) {
            return handleDirectory(request);
        } else {
            return new FileHandler(settings, router).handle(request);
        }
    }

    private Response handleDirectory(Request request) throws IOException {
        File index = FileHelper.findFile(settings.getRoot(), request.getPath().concat("/index.html"));
        if (index.exists() && settings.getAutoIndex()) {
            Request updatedRequest = request.setPath(request.getPath().concat("/index.html"));
            return new FileHandler(settings, router).handle(updatedRequest);
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
        File[] filesInDir = FileHelper.findFile(settings.getRoot(), request.getPath())
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
        File currentFile = FileHelper.findFile(settings.getRoot(), request.getPath());
        if ((currentFile.getParent() != null)) {
            links = links.concat(createLink("..", ".."));
        }
        return links;
    }

    private String createLink(String path, String resource) {
        return "<a href=\"" + path + "\">" + resource + "</a>\r\n";
    }
}
