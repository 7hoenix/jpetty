package HTTPServer;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private String action;
    private String route;
    private Map<String, String> headers;
    private Map<String, String> params;

    public Request(String action, String route) {
        this(action, route, new HashMap<>(), new HashMap<>());
    }

    private Request(String action, String route, Map<String, String> headers, Map<String, String> params) {
        this.action = action;
        this.route = route;
        this.headers = headers;
        this.params = params;
    }

    public String getAction() {
        return action;
    }

    public String getRoute() {
        return route;
    }

    public Request setAction(String action) {
        return new Request(action, this.route, this.headers, this.params);
    }

    public Request setRoute(String route) {
        return new Request(this.action, route, this.headers, this.params);
    }

    public Request setHeader(String fieldName, String fieldValue) {
        Map<String, String> updatedHeaders = new HashMap<>(headers);
        updatedHeaders.put(fieldName, fieldValue);
        return new Request(this.action, this.route, updatedHeaders, this.params);
    }

    public String getHeader(String fieldName) {
        return headers.get(fieldName);
    }

    public Request setParam(String paramName, String paramValue) {
        Map<String, String> updatedParams = new HashMap<>(params);
        updatedParams.put(paramName, paramValue);
        return new Request(this.action, this.route, this.headers, updatedParams);
    }

    public String getParam(String paramName) {
        return params.get(paramName);
    }

    public Request setHeaders(Map<String, String> headers) {
        Map<String, String> updatedHeaders = new HashMap<>(this.headers);
        updatedHeaders.putAll(headers);
        return new Request(this.action, this.route, updatedHeaders, this.params);
    }

    public Request setParams(Map<String, String> params) {
        Map<String, String> updatedParams = new HashMap<>(this.params);
        updatedParams.putAll(params);
        return new Request(this.action, this.route, this.headers, updatedParams);
    }
}
