package HTTPServer;

import java.io.*;
import java.net.Socket;

/**
 * Created by jphoenix on 8/1/16.
 */
public class SocketWrapper implements Connectible {
    private InputStream inputStream;
    private OutputStream outputStream;

    public SocketWrapper(InputStream inputStream, OutputStream outputStream)
    {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public SocketWrapper(Socket socket) throws IOException {
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    public SocketWrapper() throws IOException {
        Socket socket = new Socket("localhost", 5000);
        this.inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();
    }

    @Override
    public String read() throws IOException
    {
        return convertStreamToString(inputStream);
    }

    @Override
    public void write(String response) throws IOException {
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    public void close()
    {

    }

    private String convertStreamToString(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            if (line.isEmpty()) break;
        }
        return out.toString();
    }
}
