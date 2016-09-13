package Server;

import HTTPServer.FileHelper;
import junit.framework.TestCase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class FileHelperTest extends TestCase {
    public void test_it_finds_a_file() throws Exception {
        File file = FileHelper.findFile(new File("public"), "/index.html");

        assertEquals(71, FileHelper.findFileLength(file));
    }

    public void test_it_reads_a_file() throws Exception {
        File currentFile = new File("./public/index.html");

        byte[] fileInBytes = FileHelper.readFile(currentFile);

        assertEquals(71, fileInBytes.length);
    }

    public void test_it_reads_a_different_file() throws Exception {
        File currentFile = new File("./public/patch-content.txt");

        byte[] fileInBytes = FileHelper.readFile(currentFile);

        assertEquals(16, fileInBytes.length);
        assertEquals("default content\r", new String(fileInBytes, "UTF-8"));
    }

    public void test_it_finds_the_length_of_a_file() throws Exception {
        File currentFile = new File("./public/index.html");

        int numberOfBytes = FileHelper.findFileLength(currentFile);

        assertEquals(71, numberOfBytes);
    }

    public void test_it_finds_the_content_type_of_a_file() throws Exception {
        File currentFile = new File("./public/index.html");

        String type = FileHelper.findFileType(currentFile);

        assertEquals("text/html", type);
    }

    public void test_it_returns_the_contents_of_a_file_as_a_sha1() throws Exception {
        File currentFile = new File("./public/index.html");

        String digest = FileHelper.findFileDigest(currentFile);
        assertEquals("323879AC5222C4276CD393EBC8FE81CA5F179A29", digest);
    }

    public void test_it_changes_a_file() throws Exception {
        File currentFile = File.createTempFile("text-file", ".txt");
        currentFile.deleteOnExit();
        FileWriter writer = new FileWriter(currentFile.getAbsoluteFile());
        String initialContent = "Hi there cake";
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(initialContent);
        bufferedWriter.close();
        String updatedContent = "Nah pie is better";

        FileHelper.changeFile(currentFile, updatedContent);

        assertEquals("Nah pie is better\r", new String(FileHelper.readFile(currentFile), "UTF-8"));
    }
}
