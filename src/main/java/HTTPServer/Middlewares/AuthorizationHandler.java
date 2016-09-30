package HTTPServer.Middlewares;

import HTTPServer.Handler;
import HTTPServer.Middleware;
import HTTPServer.Request;
import HTTPServer.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;

public class AuthorizationHandler implements Middleware {
    private String userName;
    private String password;
    private String realm;
    private String[] protectedRoutes;

    public AuthorizationHandler() {
        this("", "", "", new String[0]);
    }

    private AuthorizationHandler(String userName, String password, String realm, String[] protectedRoutes) {
        this.userName = userName;
        this.password = password;
        this.realm = realm;
        this.protectedRoutes = protectedRoutes;
    }

    public AuthorizationHandler setUserName(String userName) {
        return new AuthorizationHandler(userName, this.password, this.realm, this.protectedRoutes);
    }

    public AuthorizationHandler setPassword(String password) {
        return new AuthorizationHandler(this.userName, password, this.realm, this.protectedRoutes);
    }

    public AuthorizationHandler setRealm(String realm) {
        return new AuthorizationHandler(this.userName, this.password, realm, this.protectedRoutes);
    }

    public AuthorizationHandler setProtectedRoutes(String[] protectedRoutes) {
        return new AuthorizationHandler(this.userName, this.password, this.realm, protectedRoutes);
    }

    @Override
    public Handler apply(Handler h) {
        Handler applied = (request) -> {
            if (isAuthorized(request)) {
                return h.handle(request);
            } else {
                return this.handle(request);
            }
        };
        return applied;
    }

    public Response handle(Request request) throws IOException {
        return new Response(401)
                .setHeader("WWW-Authenticate", "Basic realm=" + realm);
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
