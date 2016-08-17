import HTTPServer.Setup;
import junit.framework.TestCase;
import org.junit.rules.ExpectedException;

/**
 * Created by jphoenix on 8/8/16.
 */
public class SetupTest extends TestCase {
    public void testItHandlesThePFlagForPort() throws Exception {
        String[] args = new String[2];
        args[0] = "-p";
        args[1] = "7500";

        Setup setup = new Setup(args);

        assertEquals("public", setup.rootDirectory);
        assertEquals(7500, setup.port);
    }

    public void testItTakesTheDFlagForDirectory() throws Exception {
        String[] args = new String[2];
        args[0] = "-d";
        args[1] = "src";

        Setup setup = new Setup(args);

        assertEquals("src", setup.rootDirectory);
        assertEquals(5000, setup.port);
    }

    public void testItTakesBothFlags() throws Exception {
        String[] args = new String[4];
        args[0] = "-d";
        args[1] = "src";
        args[2] = "-p";
        args[3] = "7500";

        Setup setup = new Setup(args);

        assertEquals("src", setup.rootDirectory);
        assertEquals(7500, setup.port);
    }
}
