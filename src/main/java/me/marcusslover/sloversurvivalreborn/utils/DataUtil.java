package me.marcusslover.sloversurvivalreborn.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.*;

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

    public static void writeJsonElement(JsonObject obj, File file) throws IOException {
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new IOException("Couldn't create parent directory!");
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(new Gson().toJson(obj));
        writer.close();
    }
}
