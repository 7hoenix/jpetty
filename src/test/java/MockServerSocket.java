import HTTPServer.ServerConnectible;
import HTTPServer.Connectible;

/**
 * Created by jphoenix on 8/1/16.
 */
public class MockServerSocket implements ServerConnectible {

    public boolean listening = false;
    private Connectible socket;

    public MockServerSocket(MockSocket socket)
    {
        this.socket = socket;
    }

    public MockServerSocket()
    {
        this.socket = new MockSocket("GET /");
    }

    @Override
    public Connectible listen()
    {
        listening = true;
        return socket;
    }

    public void close() {}
}
