package CobSpecApp;

import HTTPServer.*;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public GetHandler(Setup settings) {
        this.settings = settings;
    }

    public GetHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        if (request.findQuery().contains("/redirect")) {
            Response response = new Response(302).setHeader("Location", fullPath("/"));
            return response;
        } else if (request.findQueryParams().containsKey("variable_1")) {
            byte[] body =  ("variable_1 = " + request.findQueryParams().get("variable_1") + "\r\n" +
                    "variable_2 = " + request.findQueryParams().get("variable_2")).getBytes();
            return new Response(200).setBody(body);
        } else {
            return basicGetResponse(request);
        }
    }

    private String fullPath(String path) {
        return "http://localhost:" + String.valueOf(settings.getPort() + path);
    }

    private Response basicGetResponse(Request request) throws IOException {
        File currentFile = new File(settings.getRoot().getPath().concat(request.findQuery()));
        if (currentFile.isDirectory()) {
            return handleDirectory(currentFile, request);
        } else if (currentFile.isFile()) {
            return handleFile(currentFile);
        } else {
            return new Response(404);
        }
    }

    private Response handleFile(File currentFile) throws IOException {
        Response response = new Response(200)
                .setHeader("Content-Type", findContentType(currentFile))
                .setHeader("Content-Length", new String(String.valueOf(findContentLength(currentFile))))
                .setBody(readFile(currentFile));
        return response;
    }

    private byte[] readFile(File currentFile) throws IOException {
        return Files.readAllBytes(Paths.get(currentFile.getPath()));
    }

    private int findContentLength(File currentFile) throws IOException {
        return readFile(currentFile).length;
    }

    private String findContentType(File currentFile) {
        return URLConnection.guessContentTypeFromName(currentFile.getName());
    }

    private Response handleDirectory(File currentFile, Request request) throws IOException {
        File index = new File(currentFile.getPath().concat("/index.html"));
        if (index.exists() && settings.getAutoIndex()) {
            return handleFile(index);
        } else {
            return generateDirectoryResponse(currentFile, request);
        }
    }

    private String findRoute(File currentFile) {
        String route = currentFile.getPath().replaceFirst(settings.getRoot().getPath(), "");
        if (route.isEmpty()) {
            return "/";
        } else {
            return route;
        }
    }

    private Response generateDirectoryResponse(File currentFile, Request request) throws IOException {
        String body = "<!DOCTYPE html><html lang=\"en\"><body>";
        File[] filesInDir = currentFile.listFiles(pathname -> !pathname.isHidden());
        String links = "";
        if (!(currentFile.getParent() == null)) {
            File parent = new File(currentFile.getParent());
            String parentRoute = findRoute(parent);
            links = links.concat(createLink(parentRoute, ".."));
        }
        for (File file : filesInDir) {
            String path = request.findQuery();
            if (path.equals("/"))
                path = "";
            path = path + "/" + file.getName();
            String resource = file.getName();
            links = links.concat(createLink(path, resource));
        }
        byte[] fullBody = (body + links + "</body></html>").getBytes();
        return new Response(200).setHeader("Content-Type", "text/html").setBody(fullBody);
    }

    private String createLink(String path, String resource) {
        return "<a href=\"" + path + "\">" + resource + "</a>\r\n";
    }
}
