package server.staticFileHandlers;

import server.FileHelper;
import server.Handler;
import server.Request;
import server.Response;

import java.io.File;
import java.io.IOException;

public class DirectoryHandler implements Handler {
    private File root;

    public DirectoryHandler(File root) {
        this.root = root;
    }

    public Response handle(Request request) throws IOException {
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
