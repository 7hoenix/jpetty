package Server;

import HTTPServer.FileHelper;
import junit.framework.TestCase;

import java.io.File;

public class FileHelperTest extends TestCase {
    public void test_it_finds_a_file() throws Exception {
        File file = FileHelper.findFile("public", "/index.html");

        assertEquals(71, FileHelper.findFileLength(file));
    }

    public void test_it_reads_a_file() throws Exception {
        File currentFile = new File("./public/index.html");

        byte[] fileInBytes = FileHelper.readFile(currentFile);

        assertEquals(71, fileInBytes.length);
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

    public void test_it_finds_the_route_of_a_file() throws Exception {
        File currentFile = new File("/public/brians/ping-pong-equipment/index.html");
        System.out.println(currentFile.getPath());

        String route = FileHelper.findRoute(currentFile, "public");

        assertEquals("./public/index.html", route);
    }
}
