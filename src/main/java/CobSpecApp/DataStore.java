package CobSpecApp;

import CobSpecApp.Repository;

import java.util.HashMap;

public class DataStore implements Repository {
    private HashMap<String, String> dataBank;

    public DataStore() {
        this.dataBank = new HashMap();
    }

    public void store(String key, String value) {
        dataBank.put(key, value);
    }

    public String retrieve(String key) {
        return dataBank.get(key) != null ? dataBank.get(key) : "";
    }

    public void update(String key, String value) {
        dataBank.put(key, value);
    }

    public void remove(String key) {
        dataBank.remove(key);
    }
}
