package HTTPServer.Parsers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestParamsParser {
    public final static String CR  = "" + (char) 0x0D;
    public final static String LF  = "" + (char) 0x0A;
    public final static String CRLF  = CR + LF;

    public Map<String, String> parse(byte[] rawRequest) throws UnsupportedEncodingException {
        if (rawRequest == null)
            return new HashMap<>();
        String action = findAction(rawRequest);
        String rawParams = "";
        if (action.contains("GET")) {
            rawParams = findQueryString(rawRequest);
        } else if (action.contains("PUT") || action.contains("POST")) {
            rawParams = findFormString(rawRequest);
        }
        return parseParams(rawParams);
    }

    private String findQueryString(byte[] rawRequest) {
        String fullHeader = ParserHelper.parseQuery(rawRequest, CRLF + CRLF);
        String line = ParserHelper.splitOnParameter(fullHeader, CRLF, 0);
        String rawQuery = ParserHelper.splitOnParameter(line, " ", 1);
        return (rawQuery.contains("?")) ? rawQuery.split("\\?")[1] : "";
    }

    private String findAction(byte[] rawRequest) {
        String fullHeader = ParserHelper.parseQuery(rawRequest, CRLF + CRLF);
        String line = ParserHelper.splitOnParameter(fullHeader, CRLF, 0);
        return ParserHelper.splitOnParameter(line, " ", 0);
    }

    private String findFormString(byte[] rawRequest) throws UnsupportedEncodingException {
        String full = new String(rawRequest, "UTF-8");
        return ParserHelper.splitOnParameter(full, CRLF + CRLF, 1);
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
