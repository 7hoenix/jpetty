package HTTPServer;

public class ResponseFactory {
    public Response create(byte[] header, byte[] body) {
        return new Response(header, body);
    }

    public Response create(byte[] header) {
        return new Response(header);
    }
}
