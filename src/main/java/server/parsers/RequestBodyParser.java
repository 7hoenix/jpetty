package server.parsers;

import java.io.UnsupportedEncodingException;

public class RequestBodyParser extends BasicRequestParser {
    public String parse(byte[] rawRequest) throws UnsupportedEncodingException {
        String full = new String(rawRequest, "UTF-8");
        return ParserHelper.splitOnParameter(full, CRLF + CRLF, 1);
    }
}
