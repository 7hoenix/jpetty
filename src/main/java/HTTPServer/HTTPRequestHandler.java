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
    private String rootDirectory;

    public HTTPRequestHandler(Setup settings) {
        this.rootDirectory = settings.rootDirectory;
    }

    public HTTPRequestHandler(String root) {
        this.rootDirectory = root;
    }

    public byte[] handle(InputStream request) throws IOException {
        RequestParser parser = new RequestParser(request);
        parser.parse();
        Map parsedRequest = parser.getParams();
        String uri = "./".concat(rootDirectory).concat((String) parsedRequest.get("path"));
        File currentFile = new File(uri);
        if (parsedRequest.get("path").equals("/")) {
            String route = "./".concat(rootDirectory).concat("/index.html");
            return writeFileContents(new File(route));
        } else if (currentFile.isDirectory()) {
            return generateDirectoryResponse(parsedRequest, currentFile);
        } else if (currentFile.isFile()) {
            return writeFileContents(currentFile);
        } else {
            return "HTTP/1.1 404 NOT FOUND\r\n\r\n".getBytes();
        }
    }

    private byte[] generateDirectoryResponse(Map parsedRequest, File currentFile) {
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<!DOCTYPE html><html lang=\"en\"><body>";
        String[] filesInDir = currentFile.list();
        String parentDir = currentFile.getParentFile().getName();
        String links = "";
        if (!parentDir.equals(rootDirectory)) {
            String fullPath = currentFile.getParentFile().getPath();
            Integer rootLength = rootDirectory.length();
            String upOne = fullPath.substring(rootLength + 3);
            links = links.concat("<a href=\"http://localhost:5000/" + upOne + "\">..</a>\r\n");
        }
        for (String file : filesInDir) {
            links = links.concat("<a href=\"http://localhost:5000" + parsedRequest.get("path") + "/" + file + "\">" + file + "</a>\r\n");
        }
        response += links + "</body></html>";
        return response.getBytes();
    }

    private byte[] writeFileContents(File currentFile) throws IOException {
        byte[] fileInBytes = Files.readAllBytes(Paths.get(currentFile.getPath()));
        byte[] response = addHeaders(currentFile, fileInBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(response);
        outputStream.write(fileInBytes);
        return outputStream.toByteArray();
    }

    private byte[] addHeaders(File currentFile, byte[] fileInBytes) {
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
