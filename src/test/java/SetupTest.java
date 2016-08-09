import HTTPServer.Setup;
import junit.framework.TestCase;

/**
 * Created by jphoenix on 8/8/16.
 */
public class SetupTest extends TestCase {
    public void testItCanConfigureThePublicRoot() throws Exception {
        String[] args = new String[1];
        args[0] = "src";

        Setup setup = new Setup(args);

        assertEquals("src", setup.rootDirectory);
    }

    public void testItDefaultsToPublic() throws Exception {
        String[] args = new String[0];

        Setup setup = new Setup(args);

        assertEquals("public", setup.rootDirectory);
    }

    public void testItDefaultsToPublicIfInvalid() throws Exception {
        String[] args = new String[1];
        args[0] = "cake";

        Setup setup = new Setup(args);

        assertEquals("public", setup.rootDirectory);
    }
}
