package server.parsers;

import java.util.HashMap;
import java.util.Map;

public class RequestHeaderParser extends BasicRequestParser {
    public Map<String, String> parse(byte[] rawRequest) {
        String header = ParserHelper.parseQuery(rawRequest, CRLF + CRLF);
        String[] rawHeaders = ParserHelper.splitOnParameter(header, CRLF);
        return separateHeaders(rawHeaders);
    }

    private static Map<String, String> separateHeaders(String[] rawHeaders) {
        Map<String, String> headers = new HashMap<>();
        for(String rawHeader : rawHeaders) {
            if (rawHeader.contains(": ")) {
                String[] headerAndValue = ParserHelper.splitOnParameter(rawHeader, ": ");
                headers.put(headerAndValue[0], headerAndValue[1]);
            }
        }
        return headers;
    }
}
