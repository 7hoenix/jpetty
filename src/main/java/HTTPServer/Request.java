package HTTPServer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private final byte[] fullRequest;
    private final String header;
    private final String body;

    public Request(byte[] fullRequest, String header, String body) {
        this.fullRequest = fullRequest;
        this.header = header;
        this.body = body;
    }

    public byte[] getFullRequest() {
        return fullRequest;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String findAction() {
        return getHeader().split(" ")[0];
    }

    public String findQuery() {
        return getHeader().split(" ")[1];
    }

    public String findScheme() {
        return getHeader().split(" ")[2];
    }

    public boolean isValid() {
        boolean isValid = false;
        String header = getHeader();
        if (hasProperLine(header)) {
            isValid = true;
        }
        return isValid;
    }

    private boolean hasProperLine(String header) {
        if (header.contains("\r\n") ) {
            String line = header.split("\r\n")[0];
            return hasLengthOfThree(line);
        } else {
            return hasLengthOfThree(header);
        }
    }

    private boolean hasLengthOfThree(String line) {
        if (line.contains(" ") && (line.split(" ").length == 3)) {
            return true;
        }
        return false;
    }

    public Map findQueryParams() throws UnsupportedEncodingException {
        Map params = new HashMap();
        String queryString = findQuery();
        if (queryString.contains("?")) {
            String queryParams = queryString.split("\\?")[1];
            params = parseParams(queryParams);
        }
        return params;
    }

    private Map parseParams(String queryParams) throws UnsupportedEncodingException {
        Map finalParams = new HashMap();
        if (queryParams.contains("&")) {
            String[] params = queryParams.split("&");
            for(String param : params) {
                String[] argAndValue = param.split("=");
                finalParams.put(decode(argAndValue[0]), decode(argAndValue[1]));
            }
        }
        return finalParams;
    }

    private String decode(String string) throws UnsupportedEncodingException {
        return URLDecoder.decode(string, "ASCII");
    }
}
