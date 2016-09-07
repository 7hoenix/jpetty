package HTTPServer;

import java.io.Closeable;
import java.io.IOException;

public class Connection implements Runnable, Closeable {
    private Connectable socket;
    private Setup settings;
    private DataStorage dataStore;

    public Connection(Connectable socket) {
        this(socket, new Setup(), new DataStore());
    }

    public Connection(Connectable socket, Setup settings, DataStorage dataStore) {
        this.socket = socket;
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public void run() {
        Request request = null;
        try {
            request = read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response response = null;
        try {
            Handler handler = new BasicHandler(settings, dataStore);
            System.out.println(7);
            response = handler.handle(request);
            System.out.println(8);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request read() throws IOException {
        System.out.println(0);
        Request request = new RequestParser().parse(socket.getInputStream());
        return request;
    }

    public void write(Response response) throws IOException {
        System.out.println(1);
        socket.getOutputStream().write(new ResponseFormatter().formatResponse(response));
    }

    public void close() throws IOException {
        System.out.println(2);
        socket.close();
    }
}
