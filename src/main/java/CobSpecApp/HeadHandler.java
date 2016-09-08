package CobSpecApp;

import HTTPServer.*;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HeadHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public HeadHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        dataStore.store("HEAD", request.getPath());
        File currentFile = new File(settings.getRoot().getPath().concat(request.getPath()));
        if (currentFile.isDirectory()) {
            return new Response(200).setHeader("Content-Type", "text/html");
        } else if (currentFile.isFile()) {
            return handleFile(currentFile);
        } else {
            return new Response(404);
        }
    }

    private Response handleFile(File currentFile) throws IOException {
        return new Response(200)
                .setHeader("Content-Type", findContentType(currentFile))
                .setHeader("Content-Length", new String(String.valueOf(findContentLength(currentFile))));
    }

    private byte[] readFile(File currentFile) throws IOException {
        return Files.readAllBytes(Paths.get(currentFile.getPath()));
    }

    private int findContentLength(File currentFile) throws IOException {
        return readFile(currentFile).length;
    }

    private String findContentType(File currentFile) {
        return URLConnection.guessContentTypeFromName(currentFile.getName());
    }
}
