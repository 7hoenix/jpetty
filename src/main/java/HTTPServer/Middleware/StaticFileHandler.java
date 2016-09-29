package HTTPServer.Middleware;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;
import HTTPServer.StaticFileHandlers.DirectoryHandler;
import HTTPServer.StaticFileHandlers.GetHandler;
import HTTPServer.StaticFileHandlers.PatchHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StaticFileHandler implements Handler {
    private Handler handler;
    private File staticPath;
    private boolean autoIndex;

    public StaticFileHandler(Handler handler) {
        this(handler, new File("/public"), false);
    }

    private StaticFileHandler(Handler handler, File staticPath, boolean autoIndex) {
        this.handler = handler;
        this.staticPath = staticPath;
        this.autoIndex = autoIndex;
    }

    public StaticFileHandler setPublicDirectory(File staticPath) {
        return new StaticFileHandler(this.handler, staticPath, this.autoIndex);
    }

    public StaticFileHandler setAutoIndex(boolean autoIndex) {
        return new StaticFileHandler(this.handler, this.staticPath, autoIndex);
    }

    public Response handle(Request request) throws IOException {
        File currentFile = new File(staticPath, request.getPath());
        if (currentFile.exists()) {
            return handleFile(request, currentFile);
        } else {
            return this.handler.handle(request);
        }
    }

    private Response handleFile(Request request, File currentFile) throws IOException {
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
