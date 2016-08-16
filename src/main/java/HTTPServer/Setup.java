package HTTPServer;

import java.io.File;

/**
 * Created by jphoenix on 8/8/16.
 */
public class Setup {
    public String rootDirectory;

    public Setup(String[] args) {
        if (pathGiven(args) && pathIsValid(args)) {
            this.rootDirectory = stripEntry(args[0]);
        } else {
            this.rootDirectory = "public";
        }
    }

    private String stripEntry(String arg) {
        if (arg.endsWith("/")) {
            return arg.substring(0, (arg.length() - 1));
        } else {
            return arg;
        }
    }

    private boolean pathIsValid(String[] args) {
        File file = new File(args[0]);
        return file.isDirectory();
    }

    private boolean pathGiven(String[] args) {
        return args.length > 0;
    }
}
