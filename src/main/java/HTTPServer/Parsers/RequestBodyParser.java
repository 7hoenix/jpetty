package HTTPServer.Parsers;

import java.io.UnsupportedEncodingException;

public class RequestBodyParser {
    public String parse(byte[] rawRequest) throws UnsupportedEncodingException {
        String full = new String(rawRequest, "UTF-8");
        return ParserHelper.splitOnParameter(full, "\r\n\r\n", 1);
    }
}
