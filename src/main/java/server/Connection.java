package server;

import server.parsers.RequestParser;

import java.io.Closeable;
import java.io.IOException;

public class Connection implements Runnable, Closeable {
    private Connectable connectable;
    private Handler handler;

    public Connection(Connectable connectable, Handler handler) {
        this.connectable = connectable;
        this.handler = handler;
    }

    public void run() {
        Request request = read();
        Response response = null;
        try {
            response = handler.handle(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        write(response);
        close();
    }

    public Request read() {
        Request request = null;
        try {
            request = new RequestParser().parse(connectable.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

    public void write(Response response) {
        try {
            connectable.getOutputStream().write(new ResponseFormatter().formatResponse(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connectable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
