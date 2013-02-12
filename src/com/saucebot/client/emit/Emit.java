package com.saucebot.client.emit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.saucebot.util.DataObject;

public class Emit implements DataObject {

    private String cmd;

    private JsonElement data;

    public Emit() {
        this(null);
    }

    public Emit(final String cmd) {
        setCmd(cmd);
        data = new JsonObject();
    }

    public void setCmd(final String cmd) {
        this.cmd = cmd;
    }

    public String getCmd() {
        return cmd;
    }

    @Override
    public String get(final String key) {
        return data.getAsJsonObject().get(key).getAsString();
    }

    public JsonElement getElement(final String key) {
        return data.getAsJsonObject().get(key);
    }

    public boolean hasElement(final String key) {
        return data.getAsJsonObject().has(key);
    }

    public JsonArray asArray() {
        return data.getAsJsonArray();
    }

    @Override
    public void put(final String key, final String value) {
        data.getAsJsonObject().add(key, new JsonPrimitive(value));
    }

    @Override
    public String toString() {
        return "Emit[" + cmd + ": " + data + "]";
    }

}
