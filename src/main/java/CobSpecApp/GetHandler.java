package CobSpecApp;

import HTTPServer.*;

import java.io.File;
import java.io.IOException;

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
            byte[] header = ("HTTP/1.1 302 FOUND\r\nLocation: http://localhost:" +
                    String.valueOf(settings.getPort()) + "/\r\n").getBytes();
            return new ResponseFactory().create(header);
        } else if (request.findQueryParams().containsKey("variable_1")) {
            byte[] header = "HTTP/1.1 200 OK\r\n".getBytes();
            byte[] body =  ("variable_1 = " + request.findQueryParams().get("variable_1") + "\r\n" +
                    "variable_2 = " + request.findQueryParams().get("variable_2")).getBytes();
            return new ResponseFactory().create(header, body);
        } else {
            return basicGetResponse(request);
        }
    }

    private Response basicGetResponse(Request request) throws IOException {
        File currentFile = new File(settings.getRoot().getPath().concat(request.findQuery()));
        if (currentFile.isDirectory()) {
            return handleDirectory(currentFile, request);
        } else if (currentFile.isFile()) {
            return handleFile(currentFile);
        } else {
            byte[] header = "HTTP/1.1 404 NOT FOUND\r\n".getBytes();
            return new ResponseFactory().create(header);
        }
    }

    private Response handleFile(File currentFile) throws IOException {
        Responses responder = new Responses();
        byte[] basic = responder.basicResponse();
        byte[] contentType = responder.wrapContentType(basic, currentFile);
        byte[] header = responder.wrapContentLength(responder.wrapConnection(contentType), currentFile);
        byte[] body = responder.wrapBody(new byte[0], currentFile);
        return new ResponseFactory().create(header, body);
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
        byte[] header = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n".getBytes();
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
        return new ResponseFactory().create(header, fullBody);
    }

    private String createLink(String path, String resource) {
        return "<a href=\"" + path + "\">" + resource + "</a>\r\n";
    }
}
