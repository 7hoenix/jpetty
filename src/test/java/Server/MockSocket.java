package Server;

import HTTPServer.Connectible;

import java.io.*;
import java.net.Socket;

/**
 * Created by jphoenix on 8/1/16.
 */
public class MockSocket implements Connectible, Runnable {
    private InputStream input;
    private byte[] output;
    private Integer delay = 50;

    public MockSocket(InputStream input)
    {
        this.input = input;
    }

    public InputStream getInputStream()
    {

        return input;
    }

    public void write(byte[] response)
    {
        this.output = response;
    }

    public void close()
    {

    }

    public String displayValue() throws UnsupportedEncodingException {
        return new String(output, "UTF-8");
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
