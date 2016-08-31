package HTTPServer;

import java.io.*;
import java.util.ArrayList;

public class RequestFactory {
    public Request create(InputStream inputStream) throws IOException {
        byte[] fullRequest = parseResult(inputStream);
        String header = findHeader(fullRequest);
        String body = findBody(fullRequest);
        return new Request(fullRequest, header, body);
    }

    private byte[] parseResult(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        int numberRead;
        while ((numberRead = in.read()) != -1) {
            result.write(numberRead);
            if (!in.ready())
                break;
        }
        result.flush();
        return result.toByteArray();
    }

    private String findHeader(byte[] fullRequest) {
        String header = "";
        ArrayList temp = new ArrayList(4);
        for(int character : fullRequest) {
            char c = (char) character;
            if (temp.size() > 3) {
                String test = temp.toArray()[0].toString() + temp.toArray()[1] + temp.toArray()[2] + temp.toArray()[3];
                if (test.contains("\r\n\r\n")) {
                    break;
                }
                temp.remove(0);
            }
            temp.add(c);
            header += new String(String.valueOf(c));
        }
        return header.trim();
    }

    private String findBody(byte[] fullRequest) throws IOException {
        String body = "";
        ArrayList temp = new ArrayList(4);
        boolean recording = false;
        for(int character : fullRequest) {
            char c = (char) character;
            if (temp.size() > 3) {
                String test = temp.toArray()[0].toString() + temp.toArray()[1] + temp.toArray()[2] + temp.toArray()[3];
                if (test.contains("\r\n\r\n")) {
                    recording = true;
                }
                temp.remove(0);
            }
            temp.add(c);
            if (recording)
                body += new String(String.valueOf(c));
        }
        return body.trim();
    }
}