package HTTPServer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InputParser {
    public Map parse(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String line = br.readLine();
        return populateParams(line);
    }

    private Map populateParams(String completeLine) throws IOException {
        HashMap params = new HashMap();
        if (completeLine != null) {
            String[] line = completeLine.split(" ");
            params.put("action", line[0]);
            params.put("path", line[1]);
            params.put("scheme", line[2]);
        }
        return params;
    }
}