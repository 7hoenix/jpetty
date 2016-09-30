package HTTPServer;

public interface Middleware {
    Handler apply(Handler handler);
}
