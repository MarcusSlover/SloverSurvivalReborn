package me.marcusslover.sloversurvivalreborn.code.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.marcusslover.sloversurvivalreborn.utils.API;

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
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(new Gson().toJson(obj));
        writer.close();
    }
}
