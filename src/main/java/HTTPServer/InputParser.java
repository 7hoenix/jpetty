package HTTPServer;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class InputParser {
    public Request create(InputStream inputStream) throws IOException {
//        byte[] result = parseResult(inputStream);
//        String fullLine = Requests.findLine(result);
//        String header = Requests.findHeader(result);
//        String body = Requests.findBody(result);
//        return new Request(populateParams(fullLine), header, body);
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
//        String[] result = new String[5000];
//        String line;
//        int counter = 0;
//        while ((line = br.readLine()) != null) {
//            result[counter] = line;
//            counter++;
//            if (line.isEmpty()) {
//                break;
//            }
//        }
        return new Request(new HashMap());
    }

    private byte[] parseResult(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        int numberRead;
        byte[] data = new byte[16384];

        while ((numberRead = inputStream.read(data, 0, data.length)) != -1) {
            result.write(data, 0, numberRead);
        }

        result.flush();

        return result.toByteArray();
    }

    private Map populateParams(String completeLine) throws IOException {
        HashMap params = new HashMap();
        if (completeLine != null) {
            String[] line = completeLine.split(" ");
            params.putAll(findAction(line[0]));
            params.putAll(findPathAndQueryParams(line[1]));
            params.putAll(findScheme(line[2]));
        }
        return params;
    }

    private HashMap findScheme(String httpScheme) {
        HashMap scheme = new HashMap();
        scheme.put("scheme", httpScheme);
        return scheme;
    }

    private HashMap findPathAndQueryParams(String fullPath) throws UnsupportedEncodingException {
        HashMap path = new HashMap();
        if (fullPath.contains("?")) {
            path.putAll(parseQueryParams(fullPath));
        } else {
            path.put("path", fullPath);
        }
        return path;
    }

    private HashMap parseQueryParams(String fullPath) throws UnsupportedEncodingException {
        HashMap path = new HashMap();
        String[] full = fullPath.split("\\?");
        path.put("path", full[0]);
        String[] variables = full[1].split("&");
        int i = 0;
        while (i < variables.length) {
            String[] varNameWithValue = variables[i].split("=");
            path.put(decode(varNameWithValue[0]), decode(varNameWithValue[1]));
            i++;
        }
        return path;
    }

    private String decode(String string) throws UnsupportedEncodingException {
        return URLDecoder.decode(string, "ASCII");
    }

    private HashMap findAction(String action) {
        HashMap actionMap = new HashMap();
        actionMap.put("action", action);
        return actionMap;
    }
}