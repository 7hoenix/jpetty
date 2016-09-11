package CobSpecApp;

import HTTPServer.*;
import HTTPServer.Handlers.Handler;

import java.io.IOException;

public class PatchHandler implements Handler {
    private Settings settings;
    private DataStorage dataStore;

    public PatchHandler(Settings settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        dataStore.store("PATCH", request.getPath());
        if (request.getHeader("If-Match") != null && request.getHeader("If-Match").equals("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec")) {
            dataStore.store("file-contents", "patched content");
            return new Response(204);
        } else if (request.getHeader("If-Match") != null && request.getHeader("If-Match").equals("5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0")){
            dataStore.store("file-contents", "default content");
            return new Response(204);
        } else {
            if (!dataStore.retrieve("file-contents").isEmpty()) {
                return new Response(200).setBody(dataStore.retrieve("file-contents").getBytes());
            } else {
                return new Response(200).setBody("default content".getBytes());
            }
        }
    }
}
