package HTTPServer.Middlewares;

import HTTPServer.Handler;
import HTTPServer.Middleware;
import HTTPServer.Request;
import HTTPServer.Response;

import java.io.IOException;
import java.util.ArrayList;

public class LogHandler implements Middleware {
    private ArrayList<String> log;

    public LogHandler() {
        this(new ArrayList<>());
    }

    private LogHandler(ArrayList<String> log) {
        this.log = log;
    }

    public LogHandler setLog(ArrayList<String> log) {
        return new LogHandler(log);
    }

    @Override
    public Handler apply(Handler h) {
        Handler applied = (request) -> {
            if (request.getPath().contains("/logs")) {
                return this.handle(request);
            } else {
               return h.handle(request);
            }
        };
        return applied;
    }

    public Response handle(Request request) throws IOException {
        return getRecent();
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
