package me.marcusslover.sloversurvivalreborn.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class DataUtil {
    public static JsonElement readJsonElement(File file) throws FileNotFoundException {
        FileReader fileReader = new FileReader(file);
        JsonElement element = null;
        try {
            element = API.JSON_PARSER.parse(fileReader);
        } catch (JsonSyntaxException jsonSyntaxException) {
            jsonSyntaxException.printStackTrace();
        }
        if (element == null) {
            element = new JsonObject();
        }
        return element;
    }
}
