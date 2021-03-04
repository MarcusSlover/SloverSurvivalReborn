package me.marcusslover.sloversurvivalreborn.user;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.marcusslover.sloversurvivalreborn.code.data.Data;
import me.marcusslover.sloversurvivalreborn.code.data.IFileData;
import me.marcusslover.sloversurvivalreborn.utils.DataUtil;

import java.io.File;
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
        File file = this.getFile(key, true);
        try {
            JsonElement jsonElement = DataUtil.readJsonElement(file, new JsonObject());
            this.map.put(key, new User(jsonElement.getAsJsonObject()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String key) {
        File file = this.getFile(key, true);
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
