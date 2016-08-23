package HTTPServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jphoenix on 8/23/16.
 */
public class HeadHandler implements Handler {

    private Setup settings;

    public HeadHandler(Setup settings) {
        this.settings = settings;
    }

    public byte[] handle(Map params) throws IOException {
        File currentFile = new File(settings.root.getPath().concat((String) params.get("path")));
        if (currentFile.isDirectory()) {
            return "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n".getBytes();
        } else if (currentFile.isFile()) {
            byte[] fileInBytes = Files.readAllBytes(Paths.get(currentFile.getPath()));
            return wrapHeaders(currentFile, fileInBytes);
        } else {
            return "HTTP/1.1 404 NOT FOUND\r\n\r\n".getBytes();
        }
    }

    private byte[] wrapHeaders(File currentFile, byte[] fileInBytes) {
        String header = "HTTP/1.1 200 OK\r\n";
        header = wrapContentType(header, currentFile, fileInBytes);
        header += "\r\n\r\n";
        return header.getBytes();
    }

    private String wrapContentType(String header, File currentFile, byte[] fileInBytes) {
        HashMap supportedTypes = new HashMap();
        supportedTypes.put("jpg", "image/jpeg");
        supportedTypes.put("jpeg", "image/jpeg");
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

    private String wrapContentLength(String header, byte[] fileInBytes) {
        String size = new String(String.valueOf(fileInBytes.length));
        return header + "Connection: close\r\nContent-Length: " + size;
    }
}
