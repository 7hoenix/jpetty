package HTTPServer.Middlewares;

import HTTPServer.Handler;
import HTTPServer.Middleware;
import HTTPServer.Request;
import HTTPServer.Response;
import HTTPServer.StaticFileHandlers.DirectoryHandler;
import HTTPServer.StaticFileHandlers.GetHandler;
import HTTPServer.StaticFileHandlers.PatchHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StaticFileHandler implements Middleware {
    private File staticPath;
    private boolean autoIndex;

    public StaticFileHandler() {
        this(new File("/public"), false);
    }

    private StaticFileHandler(File staticPath, boolean autoIndex) {
        this.staticPath = staticPath;
        this.autoIndex = autoIndex;
    }

    public StaticFileHandler setPublicDirectory(File staticPath) {
        return new StaticFileHandler(staticPath, this.autoIndex);
    }

    public StaticFileHandler setAutoIndex(boolean autoIndex) {
        return new StaticFileHandler(this.staticPath, autoIndex);
    }

    @Override
    public Handler apply(Handler handler) {
        Handler applied = (request) -> {
            File currentFile = new File(staticPath, request.getPath());
            if (currentFile.exists()) {
                return this.handle(request);
            } else {
                return handler.handle(request);
            }
        };
        return applied;
    }


    public Response handle(Request request) throws IOException {
        File currentFile = new File(staticPath, request.getPath());
        if (currentFile.isDirectory()) {
            return handleDirectory(request);
        } else if (supportedActions().get(request.getAction()) != null) {
            return supportedActions().get(request.getAction()).handle(request);
        } else {
            return new Response(405);
        }
    }

    private Response handleDirectory(Request request) throws IOException {
        if (autoIndex && indexExists(request)) {
            return new GetHandler(staticPath).handle(new Request(request.getPath() + "/index.html", request.getAction()));
        } else {
            return new DirectoryHandler(staticPath).handle(request);
        }
    }

    private boolean indexExists(Request request) {
        File index = new File(staticPath.getName() + request.getPath().concat("/index.html"));
        return index.exists();
    }

    private Map<String, Handler> supportedActions() {
        Map<String, Handler> supported = new HashMap<>();
        supported.put("GET", new GetHandler(staticPath));
        supported.put("PATCH", new PatchHandler(staticPath));
        return supported;
    }

}
