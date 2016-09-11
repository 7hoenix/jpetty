package HTTPServer;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String path;
    private String action;
    private Map<String, String> headers;
    private Map<String, String> params;

    public Request(String path, String action) {
        this(path, action, new HashMap<>(), new HashMap<>());
    }

    private Request(String path, String action, Map<String, String> headers, Map<String, String> params) {
        this.path = path;
        this.action = action;
        this.headers = headers;
        this.params = params;
    }

    public String getPath() {
        return path;
    }

    public String getAction() {
        return action;
    }

    public Request setPath(String path) {
        return new Request(path, this.action, this.headers, this.params);
    }

    public Request setAction(String action) {
        return new Request(this.path, action, this.headers, this.params);
    }

    public String getLine() {
        return this.action + " " + this.path + " HTTP/1.1";
    }

    public Request setHeader(String fieldName, String fieldValue) {
        Map<String, String> updatedHeaders = new HashMap<>(headers);
        updatedHeaders.put(fieldName, fieldValue);
        return new Request(this.path, this.action, updatedHeaders, this.params);
    }

    public String getHeader(String fieldName) {
        return headers.get(fieldName);
    }

    public Request setParam(String paramName, String paramValue) {
        Map<String, String> updatedParams = new HashMap<>(params);
        updatedParams.put(paramName, paramValue);
        return new Request(this.path, this.action, this.headers, updatedParams);
    }

    public String getParam(String paramName) {
        return params.get(paramName);
    }

    public Request setHeaders(Map<String, String> headers) {
        Map<String, String> updatedHeaders = new HashMap<>(this.headers);
        updatedHeaders.putAll(headers);
        return new Request(this.path, this.action, updatedHeaders, this.params);
    }

    public Request setParams(Map<String, String> params) {
        Map<String, String> updatedParams = new HashMap<>(this.params);
        updatedParams.putAll(params);
        return new Request(this.path, this.action, this.headers, updatedParams);
    }

    public Map<String, String> getParams() {
        Map<String, String> paramsCopy = new HashMap<>(this.params);
        return paramsCopy;
    }
}
