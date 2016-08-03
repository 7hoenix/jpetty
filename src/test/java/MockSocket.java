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

    public String getRequest() {
        return input;
    }

    public void sendResponse(String response) {
        output = response;
    }

    public String getResponseValue() {
        return output;
    }
}
