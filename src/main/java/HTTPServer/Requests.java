package HTTPServer;

import java.io.BufferedReader;
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
            }
            if (temp.size() > 1) {
                temp.remove(0);
            }
            temp.add(c);
            line += new String(String.valueOf(c));
        }
        return line;
    }

    public static final String findHeader(byte[] request) {
        String header = "";
        ArrayList temp = new ArrayList(4);
        for(int character : request) {
            char c = (char) character;
            if (temp.size() > 1) {
                String test = temp.toArray()[0].toString() + temp.toArray()[1];
                if (test.contains("\r\n\r\n")) {
                    break;
                }
            }
            if (temp.size() > 1) {
                temp.remove(0);
            }
            temp.add(c);
            header += new String(String.valueOf(c));
        }
        return header;
    }

    public static final String findBody(BufferedReader br) throws IOException {
        String line;
        String body = "";
        if ((line = br.readLine()) != null) {
            body += line;
        }
        return body;
    }
}
