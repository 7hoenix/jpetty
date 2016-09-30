package Server;

import CobSpecApp.Settings;
import junit.framework.TestCase;

public class SettingsTest extends TestCase {
    public void test_it_handles_the_p_flag_for_port() throws Exception {
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = "7500";

        Settings settings = new Settings(args);

        assertEquals("public", settings.getRoot().getName());
        assertEquals(7500, settings.getPort());
    }

    public void test_it_takes_the_d_flag_for_directory() throws Exception {
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = "src";

        Settings settings = new Settings(args);

        assertEquals("src", settings.getRoot().getName());
        assertEquals(5000, settings.getPort());
    }

    public void test_it_can_take_both_flags_at_once() throws Exception {
        String[] args = new String[4];
        args[0] = "-d";
        args[1] = "src";
        args[2] = "-p";
        args[3] = "7500";

        Settings settings = new Settings(args);

        assertEquals("src", settings.getRoot().getName());
        assertEquals(7500, settings.getPort());
    }

    public void test_it_takes_the_auto_index_flag() throws Exception {
        String[] args = new String[1];
        args[0] = "-ai";

        Settings settings = new Settings(args);

        assertEquals(true, settings.getAutoIndex());
    }

    public void test_it_sets_false_by_default() throws Exception {
        String[] args = new String[0];

        Settings settings = new Settings(args);

        assertEquals(false, settings.getAutoIndex());
    }
}
