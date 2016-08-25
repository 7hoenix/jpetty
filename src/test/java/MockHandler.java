import HTTPServer.Handler;
import HTTPServer.Response;
import HTTPServer.Setup;

import java.util.Map;

public class MockHandler implements Handler {
    private Setup settings;
    private boolean called;

    public MockHandler(Setup settings) {
        this.settings = settings;
        this.called = false;
    }

    public Response handle(Map params) {
        this.called = true;
        return new Response();
    }

    public boolean getCalled() {
        return called;
    }
}
