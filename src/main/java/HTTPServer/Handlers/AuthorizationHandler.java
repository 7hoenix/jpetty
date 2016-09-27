package HTTPServer.Handlers;

import HTTPServer.Request;
import HTTPServer.Response;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;

public class AuthorizationHandler implements Handler {
    private ArrayList<String> log;

    public AuthorizationHandler(ArrayList<String> log) {
        this.log = log;
    }

    public Response handle(Request request) throws UnsupportedEncodingException {
        if (requestIsAuthorized(request)) {
            String body = "";
            for (String entry : log) {
                body += entry + "\r\n";
            }
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
