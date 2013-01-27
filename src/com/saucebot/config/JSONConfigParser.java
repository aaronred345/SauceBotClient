package com.saucebot.config;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class JSONConfigParser implements ConfigParser {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    @Override
    public ConfigObject parse(final Path path) {
        try {
            Gson gson = new Gson();

            JsonElement data = gson.fromJson(Files.newBufferedReader(path, UTF8), JsonElement.class);

            return getConfigObjectFromJson(data);

        } catch (IOException ioe) {
            return new ConfigObject(null);
        }
    }

    private ConfigObject getConfigObjectFromJson(JsonElement element) {
        if (element.isJsonObject()) {
            return parseObject(element);

        } else if (element.isJsonArray()) {
            return parseArray(element);

        } else if (element.isJsonPrimitive()) {
            return parsePrimitive(element);

        } else {
            return new ConfigObject(null);
        }
    }

    private ConfigObject parsePrimitive(JsonElement element) {
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isNumber()) {
            return new ConfigObject(primitive.getAsInt());
        } else {
            return new ConfigObject(primitive.getAsString());
        }
    }

    private ConfigObject parseArray(JsonElement element) {
        List<ConfigObject> data = new ArrayList<ConfigObject>();
        for (JsonElement entry : ((JsonArray) element)) {
            data.add(getConfigObjectFromJson(entry));
        }
        return new ConfigObject(data);
    }

    private ConfigObject parseObject(JsonElement element) {
        Map<String, ConfigObject> data = new HashMap<String, ConfigObject>();
        for (Map.Entry<String, JsonElement> entry : ((JsonObject) element).entrySet()) {
            data.put(entry.getKey(), getConfigObjectFromJson(entry.getValue()));
        }
        return new ConfigObject(data);
    }
}
