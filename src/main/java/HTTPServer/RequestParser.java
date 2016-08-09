package HTTPServer;

import java.util.HashMap;

/**
 * Created by jphoenix on 8/4/16.
 */
public class RequestParser {
    public HashMap requestMap;

    private static final String STATUS = "status";
    private static final String LINE = "line";
    private static final String ACTION = "action";
    private static final String PATH = "path";
    private static final String SCHEME = "scheme";

    public RequestParser() {
        this.requestMap = new HashMap();
    }

    public HashMap parse(String request) {
        if (requestIsValid(request)) {
            parseLine(request);
        } else {
            requestMap.put(STATUS, "Request is invalid");
        }
        return requestMap;
    }

    private boolean requestIsValid(String request) {
        String[] lines = request.split("\r\n");
        String[] line = lines[0].split(" ");
        return line.length == 3;
    }

    private void parseLine(String request) {
        String[] lines = request.split("\r\n");
        String[] line = lines[0].split(" ");
        requestMap.put(LINE, lines[0]);
        requestMap.put(ACTION, line[0]);
        requestMap.put(PATH, line[1]);
        requestMap.put(SCHEME, line[2]);
    }
}