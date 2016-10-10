package server;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String path;
    private String action;
    private Map<String, String> headers;
    private Map<String, String> params;
    private String body;

    public Request(String path, String action) {
        this(path, action, new HashMap<>(), new HashMap<>(), "");
    }

    private Request(String path, String action, Map<String, String> headers, Map<String, String> params, String body) {
        this.path = path;
        this.action = action;
        this.headers = headers;
        this.params = params;
        this.body = body;
    }


    public String getPath() {
        return path;
    }

    public String getAction() {
        return action;
    }

    public Request setPath(String path) {
        return new Request(path, this.action, this.headers, this.params, this.body);
    }

    public Request setAction(String action) {
        return new Request(this.path, action, this.headers, this.params, this.body);
    }

    public String getLine() {
        return this.action + " " + this.path + " HTTP/1.1";
    }

    public Request setHeader(String fieldName, String fieldValue) {
        Map<String, String> updatedHeaders = new HashMap<>(headers);
        updatedHeaders.put(fieldName, fieldValue);
        return new Request(this.path, this.action, updatedHeaders, this.params, this.body);
    }

    public String getHeader(String fieldName) {
        return headers.get(fieldName);
    }

    public Request setParam(String paramName, String paramValue) {
        Map<String, String> updatedParams = new HashMap<>(params);
        updatedParams.put(paramName, paramValue);
        return new Request(this.path, this.action, this.headers, updatedParams, this.body);
    }

    public String getParam(String paramName) {
        return params.get(paramName);
    }

    public Request setHeaders(Map<String, String> headers) {
        Map<String, String> updatedHeaders = new HashMap<>(this.headers);
        updatedHeaders.putAll(headers);
        return new Request(this.path, this.action, updatedHeaders, this.params, this.body);
    }

    public Request setParams(Map<String, String> params) {
        Map<String, String> updatedParams = new HashMap<>(this.params);
        updatedParams.putAll(params);
        return new Request(this.path, this.action, this.headers, updatedParams, this.body);
    }

    public Map<String, String> getParams() {
        Map<String, String> paramsCopy = new HashMap<>(this.params);
        return paramsCopy;
    }

    public Request setBody(String body) {
        return new Request(this.path, this.action, this.headers, this.params, body);
    }

    public String getBody() {
        return this.body;
    }
}
