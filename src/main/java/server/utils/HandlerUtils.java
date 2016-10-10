package server.utils;

import server.Response;

public class HandlerUtils {
    public static Response redirect(String url) {
        return new Response(302)
                .setHeader("Location", url);
    }

    public static Response forbidden() {
        return new Response(403);
    }
}
