package HTTPServer;

import java.io.*;

public class BasicHandler implements Handler {
    private Settings settings;
    private DataStorage dataStore;

    public BasicHandler(Settings settings) {
        this.settings = settings;
    }

    public BasicHandler(String root) {
        this.settings = new Settings(new String[] {"-d", root});
        this.dataStore = new DataStore();
    }

    public Response handle(Request request) throws IOException {
        if (request != null) {
            return new FileHandler(settings).handle(request);
        } else {
            return new Response(400);
        }
    }
}
