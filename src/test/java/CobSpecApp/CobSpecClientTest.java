package CobSpecApp;

import HTTPServer.*;
import junit.framework.TestCase;

import java.net.ServerSocket;
import java.util.ArrayList;

public class CobSpecClientTest extends TestCase {
    public void test_it_can_require_in_a_server() throws Exception {
        Settings settings = new Settings(new String[0]);
        Repository dataStore = new DataStore();
        ArrayList<String> log = new ArrayList<>();
        Router router = CobSpecRoutes.generate(new Router(), settings, dataStore);
        ServerSocket serverSocket = new ServerSocket(settings.getPort());
        ConnectionManager serverConnection = new WrappedServerSocket(serverSocket, router, log);
        MockServer server = new MockServer(serverConnection);
        CobSpecClient client = new CobSpecClient(server);

        client.run();

        assertEquals(server.isListening(), true);
    }

    private class MockServer extends HTTPServer.Server {
        private boolean listening = false;

        public MockServer(ConnectionManager serverConnection) {
            super(serverConnection);
        }

        public void run() {
            this.listening = true;
        }

        public boolean isListening() {
            return this.listening;
        }
    }
}
