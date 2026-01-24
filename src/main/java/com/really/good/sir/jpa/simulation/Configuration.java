package com.really.good.sir.jpa.simulation;

import java.util.*;

public class Configuration {

    private final Map<String, String> properties;
    private final List<Class<?>> entityClasses = new ArrayList<>();

    public Configuration(Map<String, String> properties) {
        this.properties = properties;
    }

    public String getPropertyValue(String key) {
        String value = properties.get(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Property does not exist [" + key + "]");
        }
        return value;
    }

    public Configuration addAnnotatedClass(Class<?> cls) {
        entityClasses.add(cls);
        return this;
    }

    public List<Class<?>> getEntityClasses() {
        return entityClasses;
    }
}
