package CobSpecApp;

import HTTPServer.*;

import java.io.File;
import java.io.IOException;

public class HeadHandler implements Handler {
    private Setup settings;
    private DataStorage dataStore;

    public HeadHandler(Setup settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        Response response = new Response();
        File currentFile = new File(settings.getRoot().getPath().concat(request.findQuery()));
        if (currentFile.isDirectory()) {
            response.setHeader("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n".getBytes());
        } else if (currentFile.isFile()) {
            response.setHeader(handleFile(currentFile));
        } else {
            response.setHeader("HTTP/1.1 404 NOT FOUND\r\n".getBytes());
        }
        return response;
    }

    private byte[] handleFile(File currentFile) throws IOException {
        Responses responder = new Responses();
        byte[] basic = responder.basicResponse();
        byte[] contentType = responder.wrapContentType(basic, currentFile);
        byte[] connection = responder.wrapConnection(contentType);
        return responder.wrapContentLength(connection, currentFile);
    }
}
