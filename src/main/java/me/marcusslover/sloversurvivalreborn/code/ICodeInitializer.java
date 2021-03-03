package me.marcusslover.sloversurvivalreborn.code;

import me.marcusslover.sloversurvivalreborn.utils.API;

import java.util.HashMap;
import java.util.Map;

public interface ICodeInitializer {
    default void initialize() {}

    default String getVersion() {
        try {
            PatchVersion dataAnnotation = this.getDataAnnotation();
            return dataAnnotation.version();
        } catch (Exception e) {
            return "NULL";
        }
    }

    default void initLog(String message) {
        this.log(String.format("Successfully initialized: %s!", message));
    }

    default void log(String message) {
        this.log("CODE-INIT", message);
    }

    default void log(String prefix, String message) {
        API.getLogger().info(String.format("[%s] %s", prefix, message));
    }

    default PatchVersion getDataAnnotation() {
        PatchVersion patchVersion = ICodeInitializer.Cache.getAnnot(this.getClass());
        if (patchVersion == null)
            patchVersion = this.getClass().getDeclaredAnnotation(PatchVersion.class);
        if (patchVersion == null)
            throw new RuntimeException("ICodeInitializer class doesn't have @PatchVersion annotation");
        return patchVersion;
    }

    @SuppressWarnings("rawtypes")
    class Cache {
        private static final Map<Class<? extends ICodeInitializer>, PatchVersion> cache = new HashMap<>();

        static PatchVersion getAnnot(Class<? extends ICodeInitializer> clz) {
            return cache.get(clz);
        }

        static void setAnnot(Class<? extends ICodeInitializer> clz, PatchVersion data) {
            cache.put(clz, data);
        }
    }
}
