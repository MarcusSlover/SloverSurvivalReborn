package me.marcusslover.sloversurvivalreborn.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.marcusslover.sloversurvivalreborn.code.data.IJsonable;

import java.io.*;

public class DataUtil {
    public static JsonElement readJsonElement(File file) {
        return readJsonElement(file, null);
    }
    public static JsonElement readJsonElement(File file, JsonElement def) {
        JsonElement element;
        try {
            FileReader fileReader = new FileReader(file);
            element = API.JSON_PARSER.parse(fileReader);
        } catch (Exception ex) {
            return def;
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
