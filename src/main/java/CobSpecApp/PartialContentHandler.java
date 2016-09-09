package CobSpecApp;

import HTTPServer.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PartialContentHandler implements Handler {
    private Settings settings;
    private DataStorage dataStore;

    public PartialContentHandler(Settings settings, DataStorage dataStore) {
        this.settings = settings;
        this.dataStore = dataStore;
    }

    public Response handle(Request request) throws IOException {
        Map<String, Integer> range = findRange(request);
        byte[] content = readPartialContent(request, range);
        String contentRange = findRangeHeader(request, range);
        return new Response(206).setBody(content)
                .setHeader("Content-Range", contentRange);
    }

    private String findRangeHeader(Request request, Map<String, Integer> range) throws IOException {
        byte[] fileContents = readFile(currentFile(request));
        return "bytes " + new String(String.valueOf(range.get("start"))) + "-" +
                new String(String.valueOf(range.get("end"))) + "/" + fileContents.length;
    }

    private byte[] readPartialContent(Request request, Map<String, Integer> range) throws IOException {
        byte[] fileContents = readFile(currentFile(request));
        int resultSize = range.get("end") - range.get("start");
        byte[] result = new byte[resultSize];
        int counter = 0;
        while(counter < resultSize) {
            result[counter] = fileContents[counter + range.get("start")];
            counter++;
        }
        return result;
    }

    private Map<String, Integer> findRange(Request request) throws IOException {
        Map<String, Integer> result = new HashMap();
        int start = findStart(request);
        int end = findEnd(request);
        result.put("start", start);
        result.put("end", end);
        return result;
    }

    private int findStart(Request request) throws IOException {
        byte[] fileContents = readFile(currentFile(request));
        if (fieldValue(request).contains("=")) {
            String range = fieldValue(request).split("=")[1];
            if (range.contains("-")) {
                String[] values = range.split("-");
                if (range.startsWith("-")) {
                    return fileContents.length - Integer.parseInt(values[1]);
                } else {
                    return Integer.parseInt(values[0]);
                }
            }
        }
        return 0;
    }

    private int findEnd(Request request) throws IOException {
        byte[] fileContents = readFile(currentFile(request));
        if (fieldValue(request).contains("=")) {
            String range = fieldValue(request).split("=")[1];
            if (range.contains("-")) {
                String[] values = range.split("-");
                if (range.endsWith("-") || values[0].equals("")) {
                    return fileContents.length;
                } else {
                    return Integer.parseInt(values[1]) + 1;
                }
            }
        }
        return fileContents.length;
    }

    private String fieldValue(Request request) {
        return request.getHeader("Range");
    }

    private File currentFile(Request request) {
        return new File(settings.getRoot().getPath().concat(request.getPath()));
    }

    private byte[] readFile(File currentFile) throws IOException {
        return Files.readAllBytes(Paths.get(currentFile.getPath()));
    }
}
