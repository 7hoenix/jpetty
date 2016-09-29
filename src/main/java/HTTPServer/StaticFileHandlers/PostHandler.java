//package HTTPServer.StaticFileHandlers;
//
//import HTTPServer.Handler;
//import HTTPServer.Repository;
//import HTTPServer.Request;
//import HTTPServer.Response;
//
//import java.io.IOException;
//import java.util.Map;
//
//public class PostHandler implements Handler {
//    private Repository dataStore;
//
//    public PostHandler(Repository dataStore) {
//        this.dataStore = dataStore;
//    }
//
//    @Override
//    public Response handle(Request request) throws IOException {
//        Map<String, String> params = request.getParams();
//        for(Map.Entry<String, String> entry : params.entrySet()) {
//            dataStore.store(entry.getKey(), entry.getValue());
//        }
//        return new Response(200);
//    }
//}
