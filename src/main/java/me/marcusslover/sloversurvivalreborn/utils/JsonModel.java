package me.marcusslover.sloversurvivalreborn.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.UUID;

public class JsonModel {
    protected JsonObject jsonObject;

    public JsonModel(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public void setInteger(String path, int value) {
        jsonObject.addProperty(path, value);
    }

    public int getInteger(String path, int defaultValue) {
        if (jsonObject.has(path)) {
            return jsonObject.get(path).getAsInt();
        } else {
            this.setInteger(path, defaultValue);
        }

        return defaultValue;
    }

    public void setLong(String path, long value) {
        jsonObject.addProperty(path, value);
    }

    public long getLong(String path, long defaultValue) {
        if (jsonObject.has(path)) {
            return jsonObject.get(path).getAsLong();
        } else {
            this.setLong(path, defaultValue);
        }
        return defaultValue;
    }

    public void setDouble(String path, double value) {
        jsonObject.addProperty(path, value);
    }

    public double getDouble(String path, double defaultValue) {
        if (jsonObject.has(path)) {
            return jsonObject.get(path).getAsDouble();
        } else {
            this.setDouble(path, defaultValue);
        }
        return defaultValue;
    }

    public void setFloat(String path, float value) {
        jsonObject.addProperty(path, value);
    }

    public float getFloat(String path, float defaultValue) {
        if (jsonObject.has(path)) {
            return jsonObject.get(path).getAsFloat();
        } else {
            this.setFloat(path, defaultValue);
        }
        return defaultValue;
    }

    public void setUUID(String path, UUID value) {
        jsonObject.addProperty(path, value.toString());
    }

    public UUID getUUID(String path, UUID defaultValue) {
        if (jsonObject.has(path)) {
            return UUID.fromString(jsonObject.get(path).getAsString());
        } else {
            this.setUUID(path, defaultValue);
        }
        return defaultValue;
    }

    public void setString(String path, String value) {
        jsonObject.addProperty(path, value);
    }

    public String getString(String path, String defaultValue) {
        if (jsonObject.has(path)) {
            return jsonObject.get(path).getAsString();
        } else {
            this.setString(path, defaultValue);
        }
        return defaultValue;
    }

    public void setBoolean(String path, boolean value) {
        jsonObject.addProperty(path, value);
    }

    public boolean getBoolean(String path, boolean defaultValue) {
        if (jsonObject.has(path)) {
            return jsonObject.get(path).getAsBoolean();
        } else {
            this.setBoolean(path, defaultValue);
        }
        return defaultValue;
    }

    public void setJsonObject(String path, JsonObject value) {
        jsonObject.add(path, value);
    }

    public JsonObject getJsonObject(String path, JsonObject defaultValue) {
        if (jsonObject.has(path)) {
            return jsonObject.get(path).getAsJsonObject();
        } else {
            this.setJsonObject(path, defaultValue);
        }
        return defaultValue;
    }

    public void setJsonArray(String path, JsonArray value) {
        jsonObject.add(path, value);
    }

    public JsonArray getJsonArray(String path, JsonArray defaultValue) {
        if (jsonObject.has(path)) {
            return jsonObject.get(path).getAsJsonArray();
        } else {
            this.setJsonArray(path, defaultValue);
        }
        return defaultValue;
    }

    public JsonObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public boolean has(String key) {
        return jsonObject.has(key);
    }
}
