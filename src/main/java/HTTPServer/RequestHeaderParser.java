package HTTPServer;

import java.util.HashMap;
import java.util.Map;

public class RequestHeaderParser {
    public final static String CR  = "" + (char) 0x0D;
    public final static String LF  = "" + (char) 0x0A;
    public final static String CRLF  = CR + LF;

    public static Map<String, String> parseHeaders(byte[] rawRequest) {
        String header = findFullHeader(rawRequest);
        String[] rawHeaders = ParserHelper.splitOnParameter(header, CRLF);
        return separateHeaders(rawHeaders);
    }

    public static Map<String, String> parseLine(byte[] rawRequest) {
        String header = findFullHeader(rawRequest);
        String[] rawHeaders = ParserHelper.splitOnParameter(header, CRLF);
        String[] rawLine = ParserHelper.splitOnParameter(rawHeaders[0], " ");
        return separateLine(rawLine);
    }

    private static Map<String, String> separateLine(String[] rawLine) {
        Map<String, String> line  = new HashMap<>();
        line.put("path", parsePath(rawLine[1]));
        line.put("action", rawLine[0]);
        return line;
    }

    private static String parsePath(String queryString) {
        return (queryString.contains("?")) ? ParserHelper.splitOnParameter(queryString, "\\?")[0] : queryString;
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

    private static String findFullHeader(byte[] rawRequest) {
        return ParserHelper.parseQuery(rawRequest, CRLF + CRLF);
    }
}
