package server.middlewares;

import server.Handler;
import server.Middleware;
import server.Response;

import java.util.ArrayList;

public class WrapRequestLog implements Middleware {
    private ArrayList<String> log;

    public WrapRequestLog() {
        this(new ArrayList<>());
    }

    private WrapRequestLog(ArrayList<String> log) {
        this.log = log;
    }

    public WrapRequestLog setLog(ArrayList<String> log) {
        return new WrapRequestLog(log);
    }

    @Override
    public Handler apply(Handler h) {
        return (request) -> {
            if (request.getPath().contains("/logs")) {
                return getRecent();
            } else {
               return h.handle(request);
            }
        };
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
