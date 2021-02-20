package me.marcusslover.sloversurvivalreborn.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.marcusslover.sloversurvivalreborn.code.data.IJsonable;

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

    public static <T extends IJsonable<J>, J extends JsonElement> T readJsonable(File file, T instance) throws FileNotFoundException {
        JsonElement element = readJsonElement(file);
        //noinspection unchecked
        instance.load((J) element);
        return instance;
    }

    public static void writeJsonElement(JsonElement obj, File file) throws IOException {
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                throw new IOException("Couldn't create parent directory!");
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(new Gson().toJson(obj));
        writer.close();
    }

    public static <J extends JsonElement> void writeJsonable(IJsonable<J> jsonable, File file) throws IOException {
        writeJsonElement(jsonable.toJson(), file);
    }
}
