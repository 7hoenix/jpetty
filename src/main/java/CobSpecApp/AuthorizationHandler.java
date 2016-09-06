package CobSpecApp;

import HTTPServer.*;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class AuthorizationHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public AuthorizationHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws UnsupportedEncodingException {
        if (requestIsAuthorized(request)) {
            String body = "GET " + dataStore.retrieve("GET") + " HTTP/1.1\r\n" +
                    "PUT " + dataStore.retrieve("PUT") + " HTTP/1.1\r\n" +
                    "HEAD " + dataStore.retrieve("HEAD") + " HTTP/1.1\r\n";
            return new Response(200).setBody(body.getBytes());
        } else {
            return new Response(401).setHeader("WWW-Authenticate", "Basic realm=\"jphoenx personal server\"");
        }
    }

    private boolean requestIsAuthorized(Request request) throws UnsupportedEncodingException {
        String auth = request.getHeader("Authorization");
        if (auth != null && auth.contains(" ")) {
            String userAndPass = auth.split(" ")[1];
            String decoded = new String(Base64.getDecoder().decode(userAndPass), "UTF-8");
            if (decoded.contains(":")) {
                String[] usernameAndPass = decoded.split(":");
                return usernameAndPasswordMatchGiven(usernameAndPass[0], usernameAndPass[1]);
            }
        }
        return false;
    }

    private boolean usernameAndPasswordMatchGiven(String username, String password) {
        return username.equals("admin") && password.equals("hunter2");
    }
}
