package com.saucebot.config;

import java.util.List;
import java.util.Map;

public class ConfigObject {

    private Object value;

    public ConfigObject(final Object value) {
        this.value = value;
    }

    public Object getObject() {
        return value;
    }

    public ConfigObject getConfigObject() {
        return (ConfigObject) getObject();
    }

    public String getString() {
        if (value == null)
            return null;
        return getObject().toString();
    }

    public int getInt() {
        if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return Integer.parseInt(getString());
        }
    }

    @SuppressWarnings("unchecked")
    public List<ConfigObject> getArray() {
        return (List<ConfigObject>) value;
    }

    @SuppressWarnings("unchecked")
    public Map<String, ConfigObject> getMap() {
        return (Map<String, ConfigObject>) value;
    }

    public ConfigObject get(final String key) {
        return getMap().get(key);
    }

    public boolean has(final String key) {
        return getMap().containsKey(key);
    }

    @Override
    public String toString() {
        return "ConfigObject[" + getString() + "]";
    }

}
