package webserver;

import java.util.HashMap;
import java.util.Map;


public abstract class Http {
    Map<String, String> header = new HashMap<>();
    Map<String, String> param = new HashMap<>();

    public String getHeader(String name) {
        return header.get(name);
    }
    public String getParam(String name) {
        return param.get(name);
    }
}
