package server;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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

    public static String findFileDigest(File currentFile) throws IOException {
        try {
            MessageDigest shaDigest = MessageDigest.getInstance("SHA1");
            byte[] fileContents = readFile(currentFile);
            if (fileContents.length > 0) {
                return DatatypeConverter.printHexBinary(shaDigest.digest(Arrays.copyOf(fileContents, fileContents.length - 1)));
            } else {
                return DatatypeConverter.printHexBinary(shaDigest.digest(fileContents));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void changeFile(File currentFile, String content) {
        try {
            FileWriter writer = new FileWriter(currentFile.getAbsoluteFile());
            BufferedWriter buffered = new BufferedWriter(writer);
            buffered.write(content + "\r");
            buffered.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
