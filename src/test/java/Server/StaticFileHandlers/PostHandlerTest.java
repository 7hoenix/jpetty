//package Server.StaticFileHandlers;
//
//import HTTPServer.Repository;
//import HTTPServer.Request;
//import HTTPServer.Response;
//import HTTPServer.StaticFileHandlers.PostHandler;
//import Server.MockDataStore;
//import junit.framework.TestCase;
//
//public class PostHandlerTest extends TestCase {
//    public void test_it_changes_the_given_data_store_if_given_a_post_request() throws Exception {
//        Repository dataStore = new MockDataStore();
//        PostHandler handler = new PostHandler(dataStore);
//        Request request = new Request("/form", "POST")
//                .setParam("data", "fat cat");
//
//        handler.handle(request);
//
//        assertEquals("fat cat", dataStore.retrieve("data"));
//    }
//
//    public void test_it_does_not_blow_up_if_passed_no_params() throws Exception {
//        Repository dataStore = new MockDataStore();
//        PostHandler handler = new PostHandler(dataStore);
//        Request request = new Request("/form", "POST");
//
//        Response response = handler.handle(request);
//
//        assertEquals(null, dataStore.retrieve("data"));
//        assertEquals(200, response.getStatusCode());
//    }
//}
