package Server;

import HTTPServer.Settings;
import junit.framework.TestCase;

public class SettingsTest extends TestCase {
    public void testItHandlesThePFlagForPort() throws Exception {
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = "7500";

        Settings settings = new Settings(args);

        assertEquals("public", settings.getRoot().getName());
        assertEquals(7500, settings.getPort());
    }

    public void testItTakesTheDFlagForDirectory() throws Exception {
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = "src";

        Settings settings = new Settings(args);

        assertEquals("src", settings.getRoot().getName());
        assertEquals(5000, settings.getPort());
    }

    public void testItTakesBothFlags() throws Exception {
        String[] args = new String[4];
        args[0] = "-d";
        args[1] = "src";
        args[2] = "-p";
        args[3] = "7500";

        Settings settings = new Settings(args);

        assertEquals("src", settings.getRoot().getName());
        assertEquals(7500, settings.getPort());
    }

    public void testItTakesAnAutoIndexFlag() throws Exception {
        String[] args = new String[1];
        args[0] = "-ai";

        Settings settings = new Settings(args);

        assertEquals(true, settings.getAutoIndex());
    }

    public void testItSetsFalseByDefault() throws Exception {
        String[] args = new String[0];

        Settings settings = new Settings(args);

        assertEquals(false, settings.getAutoIndex());
    }
}
