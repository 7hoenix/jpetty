package HTTPServer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jphoenix on 8/4/16.
 */
public class HTTPRequestHandler {
    private Setup settings;

    public HTTPRequestHandler(Setup settings) {
        this.settings = settings;
    }

    public HTTPRequestHandler(String root) {
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = root;
        this.settings = new Setup(args);
    }

    public byte[] handle(InputStream request) throws IOException {
        RequestParser parser = new RequestParser(request);
        parser.parse();
        Map parsedRequest = parser.getParams();
        File currentFile = new File(settings.root.getPath().concat((String) parsedRequest.get("path")));
        return route(currentFile, parsedRequest);
    }

    private byte[] route(File currentFile, Map request) throws IOException {
        if (currentFile.isDirectory()) {
            return handleDirectory(currentFile, request);
        } else if (currentFile.isFile()) {
            return writeFileContents(currentFile);
        } else {
            return "HTTP/1.1 404 NOT FOUND\r\n\r\n".getBytes();
        }
    }

    private byte[] handleDirectory(File currentFile, Map request) throws IOException {
        File index = new File(currentFile.getPath().concat("/index.html"));
        if (request.get("path").equals("/") && index.exists()) {
            return writeFileContents(index);
        } else {
            return generateDirectoryResponse(currentFile, request);
        }
    }

    private String findRoute(File currentFile) {
        return currentFile.getPath().replaceFirst(settings.root.getPath(), "");
    }

    private byte[] generateDirectoryResponse(File currentFile, Map parsedRequest) throws IOException {
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<!DOCTYPE html><html lang=\"en\"><body>";
        File[] filesInDir = currentFile.listFiles(pathname -> !pathname.isHidden());
        String links = "";
        if (!(currentFile.getParent() == null)) {
            File parent = new File(currentFile.getParent());
            String parentRoute = findRoute(parent);
            links = links.concat(createLink(parentRoute, ".."));
        }
        for (File file : filesInDir) {
            String path = (String) parsedRequest.get("path");
            if (path.equals("/"))
                path = "";
            path = path + "/" + file.getName();
            String resource = file.getName();
            links = links.concat(createLink(path, resource));
        }
        response += links + "</body></html>";
        return response.getBytes();
    }

    private String createLink(String path, String resource) {
        return "<a href=\"" + path + "\">" + resource + "</a>\r\n";
    }

    private byte[] writeFileContents(File currentFile) throws IOException {
        byte[] fileInBytes = Files.readAllBytes(Paths.get(currentFile.getPath()));
        byte[] response = wrapHeaders(currentFile, fileInBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(response);
        outputStream.write(fileInBytes);
        return outputStream.toByteArray();
    }

    private byte[] wrapHeaders(File currentFile, byte[] fileInBytes) {
        String header = "HTTP/1.1 200 OK\r\n";
        header = wrapContentType(header, currentFile, fileInBytes);
        header += "\r\n\r\n";
        return header.getBytes();
    }

    private String wrapContentLength(String header, byte[] fileInBytes) {
        String size = new String(String.valueOf(fileInBytes.length));
        return header + "Connection: close\r\nContent-Length: " + size;
    }

    private String wrapContentType(String header, File currentFile, byte[] fileInBytes) {
        HashMap supportedTypes = new HashMap();
        supportedTypes.put("jpg", "image/jpeg");
        supportedTypes.put("gif", "image/gif");
        supportedTypes.put("png", "image/png");
        supportedTypes.put("txt", "text/plain");
        String path = currentFile.getPath();
        int finalPeriod = path.lastIndexOf(".");
        String extensionType = path.substring(finalPeriod + 1);
        if (supportedTypes.get(extensionType) != null) {
            header = header + "Content-Type: " + supportedTypes.get(extensionType) + "\r\n";
            return wrapContentLength(header, fileInBytes);
        } else {
            return header + "Content-Type: text/html";
        }
    }
}
