package me.marcusslover.sloversurvivalreborn.code.data.warp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import me.marcusslover.sloversurvivalreborn.code.data.Data;
import me.marcusslover.sloversurvivalreborn.code.data.DataUtil;
import me.marcusslover.sloversurvivalreborn.code.data.IFileData;
import me.marcusslover.sloversurvivalreborn.utils.API;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

@Data(path = "warps", type = "json")
public class WarpFileData implements IFileData<JsonObject> {
    private static WarpFileData instance;
    private final Map<String, Warp> map;

    public WarpFileData() {
        instance = this;
        this.map = new HashMap<>();
    }

    @Override
    public void read(String key) {
        File file = this.getFile(key);
        try {
            JsonObject obj = DataUtil.readJsonElement(file).getAsJsonObject();
            this.map.put(key, new Warp(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(String key) {
        File file = this.getFile(key);
        Warp warp = this.map.get(key);
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(warp.getJsonObject().toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Warp> getMap() {
        return map;
    }

    public static WarpFileData getInstance() {
        return instance;
    }
}
