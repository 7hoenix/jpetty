package HTTPServer;

import HTTPServer.Parsers.RequestParser;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

public class Connection implements Runnable, Closeable {
    private Connectable socket;
    private Router router;
    private ArrayList<String> log;

    public Connection(Connectable socket) {
        this(socket, new Router(), new ArrayList<>());
    }

    public Connection(Connectable socket, Router router, ArrayList<String> log) {
        this.socket = socket;
        this.router = router;
        this.log = log;
    }

    public void run() {
        Request request = read();
        log.add(request.getLine());
        Response response = null;
        try {
            response = router.route(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        write(response);
        close();
    }

    public Request read() {
        Request request = null;
        try {
            request = new RequestParser().parse(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return request;
    }

    public void write(Response response) {
        try {
            socket.getOutputStream().write(new ResponseFormatter().formatResponse(response));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
