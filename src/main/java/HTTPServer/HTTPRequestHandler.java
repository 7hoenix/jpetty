package HTTPServer;

import java.io.*;
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

    public String handle(String request) throws IOException {
        Map parsedRequest = new RequestParser().parse(request);
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
            return "HTTP/1.1 404 NOT FOUND\r\n\r\n";
        }
    }

    private String generateDirectoryResponse(Map parsedRequest, File currentFile) {
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n<!DOCTYPE html><html lang=\"en\"><body>";
        String[] filesInDir = currentFile.list();
        String parentDir = currentFile.getParentFile().getName();
        String links = "";
        if (!parentDir.equals(rootDirectory)) {
            links = links.concat("<a href=\"http://localhost:5000/" + parentDir + "\">..</a>\r\n");
        }
        for (String file : filesInDir) {
            links = links.concat("<a href=\"http://localhost:5000" + parsedRequest.get("path") + "/" + file + "\">" + file + "</a>\r\n");
        }
        response += links + "</body></html>";
        return response;
    }

    private String writeFileContents(File currentFile) throws IOException {
        InputStream input = new FileInputStream(currentFile);
        String fileContents = convertStreamToString(input);
        return "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + fileContents;
    }

    private String convertStreamToString(InputStream input) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        reader.close();
        return out.toString();
    }
}
