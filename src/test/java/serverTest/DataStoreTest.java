package serverTest;

import junit.framework.TestCase;
import server.DataStore;
import server.Repository;

public class DataStoreTest extends TestCase {
    public void test_it_stores_and_retrieves_an_entry() throws Exception {
        Repository dataStore = new DataStore();
        dataStore.store("cake", "yum");

        assertEquals("yum", dataStore.retrieve("cake"));
    }

    public void test_it_can_update_an_entry() throws Exception {
        Repository dataStore = new DataStore();
        dataStore.store("pie", "yum");

        dataStore.update("pie", "moar yum");

        assertEquals("moar yum", dataStore.retrieve("pie"));

    }

    public void test_it_can_delete_an_entry_in_the_store() throws Exception {
        Repository dataStore = new DataStore();
        dataStore.store("pie", "more yum");

        dataStore.remove("pie");

        assertEquals("", dataStore.retrieve("pie"));
    }
}
