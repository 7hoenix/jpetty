package HTTPServer.Middleware;

import HTTPServer.Handler;
import HTTPServer.Request;
import HTTPServer.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;

public class AuthorizationHandler implements Handler {
    private Handler handler;
    private String userName;
    private String password;
    private String realm;
    private String[] protectedRoutes;

    public AuthorizationHandler(Handler handler) {
        this(handler, "", "", "", new String[0]);
    }

    private AuthorizationHandler(Handler handler, String userName, String password, String realm, String[] protectedRoutes) {
        this.handler = handler;
        this.userName = userName;
        this.password = password;
        this.realm = realm;
        this.protectedRoutes = protectedRoutes;
    }

    public AuthorizationHandler setUserName(String userName) {
        return new AuthorizationHandler(this.handler, userName, this.password, this.realm, this.protectedRoutes);
    }

    public AuthorizationHandler setPassword(String password) {
        return new AuthorizationHandler(this.handler, this.userName, password, this.realm, this.protectedRoutes);
    }

    public AuthorizationHandler setRealm(String realm) {
        return new AuthorizationHandler(this.handler, this.userName, this.password, realm, this.protectedRoutes);
    }

    public AuthorizationHandler setProtectedRoutes(String[] protectedRoutes) {
        return new AuthorizationHandler(this.handler, this.userName, this.password, this.realm, protectedRoutes);
    }

    public Response handle(Request request) throws IOException {
        if (isAuthorized(request)) {
            return this.handler.handle(request);
        } else {
            return new Response(401)
                    .setHeader("WWW-Authenticate", "Basic realm=" + realm);
        }
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
