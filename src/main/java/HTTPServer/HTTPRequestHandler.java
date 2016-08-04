package HTTPServer;

import java.io.*;

/**
 * Created by jphoenix on 8/4/16.
 */
public class HTTPRequestHandler {
    public String handle(String request) throws IOException {
        String[] sections = request.split(" ");
        if (sections[1].equals("/")) {
            String route = "./routes/helloWorld.html";
            InputStream input = new FileInputStream(new File(route));
            String fileContents = convertStreamToString(input);
            return "HTTP/1.1 200 OK\rContent-Type: text/html\r\r" +
                    "<html><body><h1>Hello world</h1></body></html>";
        } else {
            return "HTTP/1.1 404 NOT FOUND\r\r";
        }
    }

    private String convertStreamToString(InputStream input) throws IOException {
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
