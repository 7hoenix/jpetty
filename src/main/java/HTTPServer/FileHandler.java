package HTTPServer;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler implements Handler {
    private Settings settings;

    public FileHandler(Settings settings) {
        this.settings = settings;
    }

    public Response handle(Request request) throws IOException {
        if (FileHelper.findFile(settings.getRoot(), request.getPath()).exists()) {
            return handleFileRequest(request);
        } else {
            return new Response(404);
        }
    }

    private Response handleFileRequest(Request request) throws IOException {
        if (FileHelper.findFile(settings.getRoot(), request.getPath()).isDirectory()) {
            return handleDirectory(request);
        } else {
            return handleFile(FileHelper.findFile(settings.getRoot(), request.getPath()));
        }
    }

    private Response handleFile(File currentFile) throws IOException {
        return new Response(200)
                .setHeader("Content-Type", FileHelper.findFileType(currentFile))
                .setHeader("Content-Length", new String(String.valueOf(FileHelper.findFileLength(currentFile))))
                .setBody(FileHelper.readFile(currentFile));
    }

    private Response handleDirectory(Request request) throws IOException {
        File index = FileHelper.findFile(settings.getRoot(), request.getPath().concat("/index.html"));
        if (index.exists() && settings.getAutoIndex()) {
            return handleFile(index);
        } else {
            return generateDirectoryResponse(request);
        }
    }


//    private File FileHelper.findFile(settings.getRoot(), request.getPath()) {
//        return new File(settings.getRoot().getPath().concat(request.getPath()));
//    }

//    private byte[] readFile(File currentFile) throws IOException {
//        return Files.readAllBytes(Paths.get(currentFile.getPath()));
//    }

//    private int findContentLength(File currentFile) throws IOException {
//        return readFile(currentFile).length;
//    }
//
//    private String findContentType(File currentFile) {
//        return URLConnection.guessContentTypeFromName(currentFile.getName());
//    }

    private String findRoute(File currentFile) {
        String route = currentFile.getPath().replaceFirst(settings.getRoot(), "");
        if (route.isEmpty()) {
            return "/";
        } else {
            return route;
        }
    }

    private Response generateDirectoryResponse(Request request) throws IOException {
        File currentFile = FileHelper.findFile(settings.getRoot(), request.getPath());
        String body = "<!DOCTYPE html><html lang=\"en\"><body>";
        File[] filesInDir = currentFile.listFiles(pathname -> !pathname.isHidden());
        String links = "";
        if (!(currentFile.getParent() == null)) {
            File parent = new File(currentFile.getParent());
            String parentRoute = findRoute(parent);
            links = links.concat(createLink(parentRoute, ".."));
        }
        for (File file : filesInDir) {
            String path = request.getPath();
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
