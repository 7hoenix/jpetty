package server;

import java.io.File;

public class Settings {
    private File root;
    private int port;
    private boolean autoIndex;

    public Settings(String[] args) {
        this.port = assignPort(args);
        this.root = assignRoot(args);
        this.autoIndex = assignAutoIndex(args);
    }

    public File getRoot() {
        return root;
    }

    public int getPort() {
        return port;
    }

    public boolean getAutoIndex() {
        return autoIndex;
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
