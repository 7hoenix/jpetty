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
        byte[] header;
        File currentFile = new File(settings.getRoot().getPath().concat(request.findQuery()));
        if (currentFile.isDirectory()) {
            header = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n".getBytes();
        } else if (currentFile.isFile()) {
            header = handleFile(currentFile);
        } else {
            header = "HTTP/1.1 404 NOT FOUND\r\n".getBytes();
        }
        return new ResponseFactory().create(header);
    }

    private byte[] handleFile(File currentFile) throws IOException {
        Responses responder = new Responses();
        byte[] basic = responder.basicResponse();
        byte[] contentType = responder.wrapContentType(basic, currentFile);
        byte[] connection = responder.wrapConnection(contentType);
        return responder.wrapContentLength(connection, currentFile);
    }
}
