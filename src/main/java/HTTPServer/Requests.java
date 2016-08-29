package HTTPServer;

import java.io.IOException;
import java.util.ArrayList;

public class Requests {
    public static final String findLine(byte[] request) {
        String line = "";
        ArrayList temp = new ArrayList(2);
        for(int character : request) {
            char c = (char) character;
            if (temp.size() > 1) {
                String test = temp.toArray()[0].toString() + temp.toArray()[1];
                if (test.contains("\r\n")) {
                    break;
                }
                temp.remove(0);
            }
            temp.add(c);
            line += new String(String.valueOf(c));
        }
        return line.trim();
    }

    public static final String findHeader(byte[] request) {
        String header = "";
        ArrayList temp = new ArrayList(4);
        for(int character : request) {
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

    public static final String findBody(byte[] request) throws IOException {
        String body = "";
        ArrayList temp = new ArrayList(4);
        boolean recording = false;
        for(int character : request) {
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
