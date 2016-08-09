package HTTPServer;

import java.io.File;

/**
 * Created by jphoenix on 8/8/16.
 */
public class Setup {
    public String rootDirectory;

    public Setup(String[] args) {
        if (pathGiven(args) && pathIsValid(args)) {
            this.rootDirectory = args[0];
        } else {
            this.rootDirectory = "public";
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
