package HTTPServer;

import java.io.*;
import java.util.HashMap;

/**
 * Created by jphoenix on 8/4/16.
 */
public class RequestParser {
    private InputStream in;
    private HashMap params;

    private static final String LINE = "line";
    private static final String ACTION = "action";
    private static final String PATH = "path";
    private static final String SCHEME = "scheme";
    private static final String BODYBEGINSAT = "bodyBeginsAt";

    public HashMap getParams() {
        return this.params;
    }

    public InputStream getIn() {
        return this.in;
    }

    public RequestParser(InputStream request) {
        this.in = request;
        this.params = new HashMap();
    }

    public void parse() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        StringBuilder input = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            input.append(line);
            if (line.isEmpty()) break;
            input.append("\r\n");
        }
        populateParams(input.toString());
    }

    private void populateParams(String header) throws IOException {
        String[] lines = header.split("\r\n");
        String[] line = lines[0].split(" ");
        Integer bodyBeginsAt = findBodyBeginsAt(header);
        params.put(LINE, lines[0]);
        params.put(ACTION, line[0]);
        params.put(PATH, line[1]);
        params.put(SCHEME, line[2]);
        params.put(BODYBEGINSAT, String.valueOf(bodyBeginsAt));
    }

    private Integer findBodyBeginsAt(String header) {
        return header.indexOf("\r\n\r\n") + 4;
    }
}