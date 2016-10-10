package server;

public interface Middleware {
    Handler apply(Handler handler);
}
