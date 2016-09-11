package HTTPServer;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    public Request parse(InputStream inputStream) throws IOException {
        byte[] fullRequest = parseResult(inputStream);
        Map<String, String> headers = findHeaders(fullRequest);
        Map<String, String> params = findParams(fullRequest);
        return new Request(findPath(fullRequest), findAction(fullRequest))
                .setHeaders(headers)
                .setParams(params);
    }

    private Map<String, String> findParams(byte[] input) throws IOException {
        String[] line = findHeader(input).split("\r\n")[0].split(" ");
        Map<String, String> params = new HashMap<>();
        if (line[0].contains("GET")) {
            String query = line[1];
            params = findQueryParams(query);
        } else if (line[0].contains("POST") || line[0].contains("PUT")) {
            params = parseParams(findBody(input));
        }
        return params;
    }

    private Map<String, String> findHeaders(byte[] input) {
        Map<String, String> headers = new HashMap<>();
        String[] headersWithLine = findHeader(input).split("\r\n");
        String[] rawHeaders = Arrays.copyOfRange(headersWithLine, 1, headersWithLine.length);
        for(String header : rawHeaders) {
            String[] keyAndValue = header.split(": ");
            headers.put(keyAndValue[0].trim(), keyAndValue[1].trim());
        }
        return headers;
    }

    private byte[] parseResult(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        int numberRead;
        while ((numberRead = in.read()) != -1) {
            result.write(numberRead);
            if (!in.ready())
                break;
        }
        result.flush();
        return result.toByteArray();
    }

    private String findHeader(byte[] fullRequest) {
        String header = "";
        ArrayList temp = new ArrayList(4);
        for(int character : fullRequest) {
            char c = (char) character;
            if (temp.size() > 3) {
                String test = temp.toArray()[0].toString() + temp.toArray()[1] + temp.toArray()[2] + temp.toArray()[3];
                if (test.contains("\r\n\r\n")) {
                    break;
                }
                temp.remove(0);
            }
            temp.add(c);
            header += new String(String.valueOf(c));
        }
        return header.trim();
    }

    private String findBody(byte[] fullRequest) throws IOException {
        String body = "";
        ArrayList temp = new ArrayList(4);
        boolean recording = false;
        for(int character : fullRequest) {
            char c = (char) character;
            if (temp.size() > 3) {
                String test = temp.toArray()[0].toString() + temp.toArray()[1] + temp.toArray()[2] + temp.toArray()[3];
                if (test.contains("\r\n\r\n")) {
                    recording = true;
                }
                temp.remove(0);
            }
            temp.add(c);
            if (recording)
                body += new String(String.valueOf(c));
        }
        return body.trim();
    }

    public String findAction(byte[] fullRequest) {
        return findHeader(fullRequest).split(" ")[0];
    }

    public String findQuery(byte[] fullRequest) {
        return findHeader(fullRequest).split(" ")[1];
    }

    private String findPath(byte[] fullRequest) {
        String query = findQuery(fullRequest);
        if (query.contains("?")) {
            return query.split("\\?")[0];
        } else {
            return query;
        }
    }

    public Map findQueryParams(String query) throws UnsupportedEncodingException {
        Map params = new HashMap();
        if (query.contains("?")) {
            String queryParams = query.split("\\?")[1];
            params = parseParams(queryParams);
        }
        return params;
    }

    private Map parseParams(String queryParams) throws UnsupportedEncodingException {
        Map finalParams = new HashMap();
        if (queryParams.contains("&")) {
            String[] params = queryParams.split("&");
            for(String param : params) {
                finalParams = putArgAndValue(finalParams, param);
            }
        } else {
            finalParams = putArgAndValue(finalParams, queryParams);
        }
        return finalParams;
    }

    private Map putArgAndValue(Map finalParams, String param) throws UnsupportedEncodingException {
        if (param.contains("=")) {
            String[] argAndValue = param.split("=");
            finalParams.put(decode(argAndValue[0]), decode(argAndValue[1]));
        }
        return finalParams;
    }

    private String decode(String string) throws UnsupportedEncodingException {
        return URLDecoder.decode(string, "ASCII");
    }
}