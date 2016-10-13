package server.middlewares;

import server.Handler;
import server.Middleware;
import server.Request;
import server.Response;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;

public class WrapBasicAuth implements Middleware {
    private String userName;
    private String password;
    private String realm;
    private String[] protectedRoutes;

    public WrapBasicAuth() {
        this("", "", "", new String[0]);
    }

    private WrapBasicAuth(String userName, String password, String realm, String[] protectedRoutes) {
        this.userName = userName;
        this.password = password;
        this.realm = realm;
        this.protectedRoutes = protectedRoutes;
    }

    public WrapBasicAuth withUserName(String userName) {
        return new WrapBasicAuth(userName, this.password, this.realm, this.protectedRoutes);
    }

    public WrapBasicAuth withPassword(String password) {
        return new WrapBasicAuth(this.userName, password, this.realm, this.protectedRoutes);
    }

    public WrapBasicAuth withRealm(String realm) {
        return new WrapBasicAuth(this.userName, this.password, realm, this.protectedRoutes);
    }

    public WrapBasicAuth withProtectedRoutes(String[] protectedRoutes) {
        return new WrapBasicAuth(this.userName, this.password, this.realm, protectedRoutes);
    }

    @Override
    public Handler apply(Handler h) {
        return (request) -> {
            if (isAuthorized(request)) {
                return h.handle(request);
            } else {
                return new Response(401)
                        .setHeader("WWW-Authenticate", "Basic realm=" + realm);
            }
        };
    }

    private boolean isAuthorized(Request request) {
        return (destinationIsNotProtected(request) || userAuthenticates(request));
    }

    private boolean destinationIsNotProtected(Request request) {
        return !Arrays.asList(protectedRoutes).contains(request.getPath());
    }

    private boolean userAuthenticates(Request request) {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.contains(" ")) {
            String userAndPass = auth.split(" ")[1];
            String decoded = null;
            try {
                decoded = new String(Base64.getDecoder().decode(userAndPass), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (decoded.contains(":")) {
                String[] usernameAndPass = decoded.split(":");
                return usernameAndPass[0].contains(this.userName) && usernameAndPass[1].contains(this.password);
            }
        }
        return false;
    }
}
