import HTTPServer.Connectible;

import java.io.*;
import java.net.Socket;

/**
 * Created by jphoenix on 8/1/16.
 */
public class MockSocket implements Connectible, Runnable {
    private String input;
    private String output;
    private Integer delay = 50;

    public MockSocket(String input)
    {
        this.input = input;
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

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
            Socket socket = new Socket("localhost", 5000);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
