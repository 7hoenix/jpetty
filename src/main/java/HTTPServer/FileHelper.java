package HTTPServer;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

    public static File findFile(File root, String path) {
        return new File(root.getPath().concat(path));
    }

    public static byte[] readFile(File currentFile) throws IOException {
        return (isValidTarget(currentFile)) ? Files.readAllBytes(Paths.get(currentFile.getPath())) : new byte[0];
    }

    public static int findFileLength(File currentFile) throws IOException {
        return (isValidTarget(currentFile)) ? (int) Files.size(currentFile.toPath()) : 0;
    }

    public static String findFileType(File currentFile) {
        return (isValidTarget(currentFile)) ? URLConnection.guessContentTypeFromName(currentFile.getName()) : "";
    }

    private static boolean isValidTarget(File currentFile) {
        return (currentFile != null && currentFile.isFile());
    }
}
