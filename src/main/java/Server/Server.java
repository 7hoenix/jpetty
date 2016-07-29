package Server;

/**
 * Created by jphoenix on 7/28/16.
 */
public class Server {
    private SocketDriver socket;

    public Server(SocketDriver sd) {
        socket = sd;
    }

    public void run() {
        socket.getInputStream();
        socket.getOutputStream();
    }
}
