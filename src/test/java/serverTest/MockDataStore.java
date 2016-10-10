package serverTest;

import server.Repository;

import java.util.HashMap;
import java.util.Map;

public class MockDataStore implements Repository {
        private Map<String, String> data;

        public MockDataStore() {
                this.data = new HashMap<>();
        }

        @Override
        public void store(String key, String value) {
                this.data.put(key, value);
        }

        @Override
        public String retrieve(String key) {
                return this.data.get(key);
        }

        @Override
        public void update(String key, String value) {
                this.data.put(key, value);
        }

        @Override
        public void remove(String key) {
            this.data.remove(key);
        }
}
