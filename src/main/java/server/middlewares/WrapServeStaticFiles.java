package server.middlewares;

import server.Handler;
import server.Middleware;
import server.Request;
import server.Response;
import server.staticFileHandlers.DirectoryHandler;
import server.staticFileHandlers.GetHandler;
import server.staticFileHandlers.PatchHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WrapServeStaticFiles implements Middleware {
    private File staticPath;
    private boolean autoIndex;

    public WrapServeStaticFiles() {
        this(new File("/public"), false);
    }

    private WrapServeStaticFiles(File staticPath, boolean autoIndex) {
        this.staticPath = staticPath;
        this.autoIndex = autoIndex;
    }

    public WrapServeStaticFiles withPublicDirectory(File staticPath) {
        return new WrapServeStaticFiles(staticPath, this.autoIndex);
    }

    public WrapServeStaticFiles withAutoIndex(boolean autoIndex) {
        return new WrapServeStaticFiles(this.staticPath, autoIndex);
    }

    @Override
    public Handler apply(Handler handler) {
        return (request) -> {
            File currentFile = new File(staticPath, request.getPath());
            if (currentFile.exists()) {
                return serveStaticFile(request);
            } else {
                return handler.handle(request);
            }
        };
    }

    public Response serveStaticFile(Request request) throws IOException {
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
