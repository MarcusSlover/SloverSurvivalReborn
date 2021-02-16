package me.marcusslover.sloversurvivalreborn.utils;

public interface ICodeInitializer {
    default void initialize() {}

    default void initLog(String message) {
        this.log(String.format("Successfully initialized: %s!", message));
    }

    default void log(String message) {
        API.getLogger().info(String.format("[%s] %s", "CODE-INIT", message));
    }
}
