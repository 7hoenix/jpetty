package server.parsers;

import server.Request;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class RequestParser {
    public Request parse(InputStream inputStream) throws IOException {
        byte[] rawRequest = readFromInputStream(inputStream);
        Map<String, String> headers = new RequestHeaderParser().parse(rawRequest);
        Map<String, String> line = new RequestLineParser().parse(rawRequest);
        Map<String, String> params = new RequestParamsParser().parse(rawRequest);
        String body = new RequestBodyParser().parse(rawRequest);
        return new Request(line.get("path"), line.get("action"))
                .setHeaders(headers)
                .setParams(params)
                .setBody(body);
    }

    private byte[] readFromInputStream(InputStream inputStream) throws IOException {
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
}