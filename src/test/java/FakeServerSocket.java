import HTTPServer.ServerConnectable;
import HTTPServer.Connectable;

/**
 * Created by jphoenix on 8/1/16.
 */
public class FakeServerSocket implements ServerConnectable {

    public boolean listening = false;
    private Connectable socket;

    public FakeServerSocket(FakeSocket socket) {
        this.socket = socket;
    }

    public FakeServerSocket() {
        socket = new FakeSocket();
    }

    @Override
    public Connectable listenForRequest() {
        listening = true;
        return socket;
    }
}
