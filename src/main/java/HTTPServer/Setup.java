package HTTPServer;

import java.io.File;

/**
 * Created by jphoenix on 8/8/16.
 */
public class Setup {
    public String[] args;
    public File root;
    public int port;
    public boolean autoIndex;

    public Setup(String[] args) {
        this.args = args;
        this.port = assignPort(args);
        this.root = assignRoot(args);
        this.autoIndex = assignAutoIndex(args);
    }

    private boolean assignAutoIndex(String[] args) {
        if (flagFound(args, "-ai")) {
            return true;
        } else {
            return false;
        }
    }

    private int assignPort(String[] args) {
        if (flagFound(args, "-p")) {
            return Integer.parseInt(argAfterFlag(args, "-p"));
        } else {
            return 5000;
        }
    }

    private File assignRoot(String[] args) {
        if (flagFound(args, "-d")) {
            return new File(stripEntry(argAfterFlag(args, "-d")));
        } else {
            return new File("public");
        }
    }

    private String argAfterFlag(String[] args, String flag) {
        int i = 0;
        for (String arg : args) {
            if (arg.equals(flag))
                break;
            i++;
        }
        return args[i + 1];
    }

    private boolean flagFound(String[] args, String flag) {
        for (String arg : args) {
            if (arg.equals(flag))
                return true;
        }
        return false;
    }

    private String stripEntry(String arg) {
        if (arg.endsWith("/")) {
            return arg.substring(0, (arg.length() - 1));
        } else {
            return arg;
        }
    }
}
