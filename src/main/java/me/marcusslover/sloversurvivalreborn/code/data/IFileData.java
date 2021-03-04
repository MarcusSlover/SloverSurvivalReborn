package me.marcusslover.sloversurvivalreborn.code.data;

import me.marcusslover.sloversurvivalreborn.SloverSurvivalReborn;
import me.marcusslover.sloversurvivalreborn.code.event.IMenu;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface IFileData<T> {
    void read(String key);

    void save(String key);

    @SuppressWarnings("ResultOfMethodCallIgnored")
    default File getFile(String key, boolean createIfNotExists) {
        Data data = getDataAnnotation();
        File dataFolder = getDataFolder();

        File dataFile = new File(dataFolder, key + "." + data.type());
        if (createIfNotExists && !dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataFile;
    }

    default File getDataFolder() {
        SloverSurvivalReborn instance = SloverSurvivalReborn.getInstance();

        Data data = getDataAnnotation();
        File dataFolder = new File(instance.getDataFolder(), data.path());
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }
        return dataFolder;
    }

    default Data getDataAnnotation() {
        Data data = Cache.getAnnot(this.getClass());
        if (data == null) {
            data = this.getClass().getDeclaredAnnotation(Data.class);
            IFileData.Cache.setAnnot(this.getClass(), data);
        }
        if (data == null)
            throw new RuntimeException("IFileData class doesn't have @Data annotation");
        return data;
    }

    default void onDisable() {

    }

    @SuppressWarnings("rawtypes")
    class Cache {
        private static final Map<Class<? extends IFileData>, Data> cache = new HashMap<>();

        static Data getAnnot(Class<? extends IFileData> clz) {
            return cache.get(clz);
        }
        static void setAnnot(Class<? extends IFileData> clz, Data data) {
            cache.put(clz, data);
        }
    }
}
