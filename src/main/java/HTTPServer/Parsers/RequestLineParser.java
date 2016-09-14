package HTTPServer.Parsers;

import java.util.HashMap;
import java.util.Map;

public class RequestLineParser {
    public final static String CR  = "" + (char) 0x0D;
    public final static String LF  = "" + (char) 0x0A;
    public final static String CRLF  = CR + LF;

    public Map<String, String> parse(byte[] rawRequest) {
        String header = ParserHelper.parseQuery(rawRequest, CRLF + CRLF);
        String rawHeaders = ParserHelper.splitOnParameter(header, CRLF, 0);
        String[] rawLine = ParserHelper.splitOnParameter(rawHeaders, " ");
        return separateLine(rawLine);
    }

    private static Map<String, String> separateLine(String[] rawLine) {
        Map<String, String> line  = new HashMap<>();
        line.put("action", rawLine[0]);
        line.put("path", parsePath(rawLine[1]));
        return line;
    }

    private static String parsePath(String queryString) {
        return (queryString.contains("?")) ? queryString.split("\\?")[0] : queryString;
    }
}
