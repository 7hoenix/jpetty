package HTTPServer;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

    public static File findFile(String root, String path) {
        return new File(root.concat(path));
    }

    public static byte[] readFile(File currentFile) throws IOException {
        return (isValidTarget(currentFile)) ? Files.readAllBytes(Paths.get(currentFile.getPath())) : new byte[0];
    }

    public static int findFileLength(File currentFile) throws IOException {
        return (isValidTarget(currentFile)) ? readFile(currentFile).length : 0;
    }

    public static String findFileType(File currentFile) {
        return (isValidTarget(currentFile)) ? URLConnection.guessContentTypeFromName(currentFile.getName()) : "";
    }

    public static String findRoute(File currentFile, String root) {
        String route = currentFile.getPath().replaceFirst(root, "");
        if (route.isEmpty()) {
            return "/";
        } else {
            return route;
        }
    }

    private static boolean isValidTarget(File currentFile) {
        return (currentFile != null && currentFile.isFile());
    }
}
