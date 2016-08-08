import HTTPServer.Connectible;

import java.io.*;

/**
 * Created by jphoenix on 8/1/16.
 */
public class MockSocket implements Connectible {
    private String input;
    private String output;
    private OutputStream outputStream;

    public MockSocket(String input)
    {
        this.input = input;
    }

    public MockSocket(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String read()
    {
        return input;
    }

    public void write(String response)
    {
        this.output = response;
    }

    public void close()
    {

    }

    public String displayValue()
    {
        return output;
    }
}
