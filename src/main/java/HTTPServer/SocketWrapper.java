package HTTPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by jphoenix on 8/1/16.
 */
public class SocketWrapper implements Connectible {
    private InputStream input;

    public SocketWrapper(InputStream inputStream) {
       input = inputStream;
    }

    @Override
    public String read() throws IOException {
        return convertStreamToString(input);
    }

    @Override
    public void write(String response) {
    }

    public void close() {}

    private String convertStreamToString(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
        }
        reader.close();
        return out.toString();
    }
}
