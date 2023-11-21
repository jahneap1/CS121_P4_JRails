package jrails;

import java.lang.reflect.*;
import java.util.*;

public class JRouter {
    static private String[] validVerbs = { "GET", "HEAD", "POST", "PUT", "DELETE", "CONNECT", "OPTIONS", "TRACE",
            "PATCH" };

    static Map<String, Map<String, String>> routes = new HashMap<>();
    static String className;

   
    public void addRoute(String verb, String path, Class c, String method) {
        Set<String> validVerbSet = new HashSet<>();
        validVerbSet.addAll(Arrays.asList(validVerbs));
        assert validVerbSet.contains(verb) : "Error: Invalid Route Verb!";

        className = c.getName();

        if (!routes.containsKey(verb)) {
            Map<String, String> getRoutes = new HashMap<>();
            getRoutes.put(path, method);
            routes.put(verb, getRoutes);
            return;
        }

        routes.get(verb).put(path, method);
    }

    private String getControllerMethod(String verb, String path) {
        if (!routes.containsKey(verb)) {
            throw new UnsupportedOperationException();
        }
        if (!routes.get(verb).containsKey(path)) {
            throw new UnsupportedOperationException("key not found");
        }

        return routes.get(verb).get(path);
    }

    public String getRoute(String verb, String path) {
        try {
            return className + "#" + getControllerMethod(verb, path);
        } catch (Exception e) {
            return null;
        }
    }

    
    public Html route(String verb, String path, Map<String, String> params) {
        try {
            Class<?> c = Class.forName(className);
            Method m = c.getMethod(getControllerMethod(verb, path),Map.class);
            Html result = (Html) m.invoke(c, params);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        throw new UnsupportedOperationException();
    }
}