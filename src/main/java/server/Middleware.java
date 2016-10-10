package server;

public interface Middleware {
    Handler myApply(Handler handler);
}
