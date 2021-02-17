package me.marcusslover.sloversurvivalreborn.code.data;

import me.marcusslover.sloversurvivalreborn.SloverSurvivalReborn;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;

public interface IFileData<T> {
    void read(String key);

    void save(String key);

    @SuppressWarnings("ResultOfMethodCallIgnored")
    default File getFile(String key) {
        SloverSurvivalReborn instance = SloverSurvivalReborn.getInstance();

        Class<? extends IFileData> aClass = this.getClass();
        Annotation[] annotations = aClass.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof Data) {
                Data data = (Data) annotation;
                String path = data.path();
                String type = data.type();

                File playersFolder = new File(instance.getDataFolder(), path);
                if (playersFolder.exists()) {
                    playersFolder.mkdirs();
                }

                File playerData = new File(playersFolder, key + "." + type);
                if (playerData.exists()) {
                    try {
                        playerData.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return playerData;
            }
        }
        return null;
    }

    default void onDisable() {

    }
}
