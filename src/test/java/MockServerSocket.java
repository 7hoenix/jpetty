import HTTPServer.ServerConnectable;
import HTTPServer.Connectible;

/**
 * Created by jphoenix on 8/1/16.
 */
public class MockServerSocket implements ServerConnectable {

    public boolean listening = false;
    private Connectible socket;

    public MockServerSocket(MockSocket socket) {
        this.socket = socket;
    }

    public MockServerSocket() {
        socket = new MockSocket("GET /");
    }

    @Override
    public Connectible listenForRequest() {
        listening = true;
        return socket;
    }
}
