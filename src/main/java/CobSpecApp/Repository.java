package CobSpecApp;

public interface Repository {
    void store(String key, String value);
    String retrieve(String key);
    void update(String key, String value);
    void remove(String key);
}
