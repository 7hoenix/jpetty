package HTTPServer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jphoenix on 8/4/16.
 */
public class RequestParser {
    private static final String LINE = "line";
    private static final String HEADERS = "headers";
    private static final String BODY = "body";

    public Map parse(String request) {
        HashMap requestMap = new HashMap();
        String[] splitForBody = request.split("\r\r");
        if (splitForBody.length > 1) {
            requestMap.put(BODY, splitForBody[1]);
        }
        String[] sections = splitForBody[0].split("\r");
        requestMap.put(LINE, sections[0]);
        requestMap.put(HEADERS, sections[1]);
        return requestMap;
    }
}
