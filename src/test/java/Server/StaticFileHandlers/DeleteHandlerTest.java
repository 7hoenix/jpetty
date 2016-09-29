//package Server.StaticFileHandlers;
//
//import HTTPServer.Repository;
//import HTTPServer.Request;
//import Server.MockDataStore;
//import junit.framework.TestCase;
//
//public class DeleteHandlerTest extends TestCase {
//    public void test_it_removes_the_data_store_data_if_remove_is_called() throws Exception {
//        Repository dataStore = new MockDataStore();
//        dataStore.store("data", "cake yo");
//        Request request = new Request("/form", "DELETE");
//        DeleteHandler handler = new DeleteHandler(dataStore);
//
//        handler.handle(request);
//
//        assertEquals(null, dataStore.retrieve("data"));
//    }
//}
