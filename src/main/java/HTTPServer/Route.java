package HTTPServer;

import java.util.regex.Pattern;

public class Route {
    private String path;
    private String action;
    private Handler handler;

    public Route() {
        this("", "", new BasicHandler());
    }

    private Route(String path, String action, Handler handler) {
        this.path = path;
        this.action = action;
        this.handler = handler;
    }

    public Route setPath(String path) {
        return new Route(path, this.action, this.handler);
    }

    public Route setAction(String action) {
        return new Route(this.path, action, this.handler);
    }

    public Route setHandler(Handler handler) {
        return new Route(this.path, this.action, handler);
    }

    public String getPath() {
        return this.path;
    }

    public String getAction() {
        return this.action;
    }

    public Handler get(String path, String action) {
        return (matchesPath(path) && action.equals(this.action)) ? handler : null;
    }

    private boolean matchesPath(String path) {
        return path.equals(this.path) || Pattern.matches(this.path, path);
    }

    public boolean isAMatch(String path, String action) {
        return (matchesPath(path) && action.equals(this.action));
    }
}
