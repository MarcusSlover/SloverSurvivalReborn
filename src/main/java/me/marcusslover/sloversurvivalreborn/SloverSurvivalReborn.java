package me.marcusslover.sloversurvivalreborn;

import me.marcusslover.sloversurvivalreborn.code.CodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.command.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SloverSurvivalReborn extends JavaPlugin {
    private static SloverSurvivalReborn instance;

    @Override
    public void onEnable() {
        instance = this;
        log().info("Initializing the plugin...");
        CodeInitializer codeInitializer = new CodeInitializer();
        codeInitializer.add(new CommandHandler());
        log().info("Initialization done.");
    }

    @Override
    public void onDisable() {
        log().info("The plugin was disabled!");
    }

    public static Logger log() {
        return getInstance().getLogger();
    }

    public static SloverSurvivalReborn getInstance() {
        return instance;
    }
}
