package HTTPServer.Middleware;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;

import java.io.IOException;
import java.util.ArrayList;

public class LogHandler implements Handler {
    private Handler handler;
    private ArrayList<String> log;

    public LogHandler(Handler handler) {
        this(handler, new ArrayList<>());
    }

    private LogHandler(Handler handler, ArrayList<String> log) {
        this.handler = handler;
        this.log = log;
    }

    public LogHandler setLog(ArrayList<String> log) {
        return new LogHandler(this.handler, log);
    }

    public Response handle(Request request) throws IOException {
        return (request.getPath().contains("/logs")) ? getRecent() : this.handler.handle(request);
    }

    private Response getRecent() {
        StringBuilder builder = new StringBuilder();
        for (String entry : log) {
            builder.append(entry);
            builder.append("\r\n");
        }
        return new Response(200).setBody(builder.toString().getBytes());
    }
}
