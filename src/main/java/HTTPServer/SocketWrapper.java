package HTTPServer;

import java.io.*;
import java.net.Socket;

public class SocketWrapper implements Connectible {
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public SocketWrapper(Socket socket, InputStream inputStream, OutputStream outputStream)
    {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public SocketWrapper(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    @Override
    public InputStream getInputStream() throws IOException
    {
        return inputStream;
    }

    @Override
    public void write(byte[] response) throws IOException {
        outputStream.write(response);
    }

    @Override
    public void close()
    {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
