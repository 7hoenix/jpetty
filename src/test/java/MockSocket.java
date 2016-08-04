import HTTPServer.Connectible;

import java.io.*;

/**
 * Created by jphoenix on 8/1/16.
 */
public class MockSocket implements Connectible {
    private String input;
    private String output = "";

    public MockSocket(String in) {
        input = in;
    }

    public String read() {
        return input;
    }

    public void write(String response) {
        output = response;
    }

    public void close() {

    }

    public String getResponseValue() {
        return output;
    }
}
