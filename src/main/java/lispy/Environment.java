package lispy;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class Environment {

    private Map<String, Object> lib = new LinkedHashMap<>();
    private Map<String, Object> heap = new LinkedHashMap<>();

    private Environment() {
        lib.put("length", Functions.LENGTH);
        lib.put("*", Functions.MULTIPLY);
        lib.put("+", Functions.ADD);
        lib.put("-", Functions.SUBTRACT);
        lib.put("/", Functions.DIVIDE);
    }

    public static Environment standardEnv() {
        return new Environment();
    }

    public Object getFn(String key) {
        return lib.get(key);
    }

    public void setFn(String key, Object value) {
        lib.put(key, value);
    }

    public Object get(String key) {
        return heap.get(key);
    }

    public void set(String key, Object value) {
        heap.put(key, value);
    }
}
