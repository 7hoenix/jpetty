package Server;

import HTTPServer.Setup;
import junit.framework.TestCase;

public class SetupTest extends TestCase {
    public void testItHandlesThePFlagForPort() throws Exception {
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = "7500";

        Setup setup = new Setup(args);

        assertEquals("public", setup.getRoot().getName());
        assertEquals(7500, setup.getPort());
    }

    public void testItTakesTheDFlagForDirectory() throws Exception {
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = "src";

        Setup setup = new Setup(args);

        assertEquals("src", setup.getRoot().getName());
        assertEquals(5000, setup.getPort());
    }

    public void testItTakesBothFlags() throws Exception {
        String[] args = new String[4];
        args[0] = "-d";
        args[1] = "src";
        args[2] = "-p";
        args[3] = "7500";

        Setup setup = new Setup(args);

        assertEquals("src", setup.getRoot().getName());
        assertEquals(7500, setup.getPort());
    }

    public void testItTakesAnAutoIndexFlag() throws Exception {
        String[] args = new String[1];
        args[0] = "-ai";

        Setup setup = new Setup(args);

        assertEquals(true, setup.getAutoIndex());
    }

    public void testItSetsFalseByDefault() throws Exception {
        String[] args = new String[0];

        Setup setup = new Setup(args);

        assertEquals(false, setup.getAutoIndex());
    }
}
