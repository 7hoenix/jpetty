package HTTPServer;

/**
 * Created by jphoenix on 8/1/16.
 */
public interface Connectible {
    String getRequest();
    void sendResponse(String response);
}
