package me.marcusslover.sloversurvivalreborn.code.data.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.marcusslover.sloversurvivalreborn.code.data.Data;
import me.marcusslover.sloversurvivalreborn.code.data.IFileData;
import me.marcusslover.sloversurvivalreborn.utils.API;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

@Data(path = "players", type = "json")
public class UserFileData implements IFileData<JsonObject> {
    private static UserFileData instance;
    private final Map<String, User> map;

    public UserFileData() {
        instance = this;
        this.map = new HashMap<>();
    }

    @Override
    public void read(String key) {
        File file = this.getFile(key);
        try {
            FileReader fileReader = new FileReader(file);
            JsonElement jsonElement = null;
            try {
                jsonElement = API.JSON_PARSER.parse(fileReader);
            } catch (JsonSyntaxException jsonSyntaxException) {
                jsonSyntaxException.printStackTrace();
            }
            if (jsonElement == null) {
                jsonElement = new JsonObject();
            }
            JsonObject asJsonObject = jsonElement.getAsJsonObject();
            this.map.put(key, new User(asJsonObject));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String key) {
        File file = this.getFile(key);
        User user = this.map.get(key);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(user.getJsonObject().toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, User> getMap() {
        return map;
    }

    public static UserFileData getInstance() {
        return instance;
    }
}
