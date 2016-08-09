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

    public HTTPRequestHandler() {
        this.rootDirectory = "public";
    }

    public String handle(String request) throws IOException
    {
        Map parsedRequest = new RequestParser().parse(request);
        if (parsedRequest.get("path").equals("/")) {
            String route = "./".concat(rootDirectory).concat("/index.html");
            InputStream input = new FileInputStream(new File(route));
            String fileContents = convertStreamToString(input);
            return "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" + fileContents;
        } else {
            return "HTTP/1.1 404 NOT FOUND\r\n\r\n";
        }
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
