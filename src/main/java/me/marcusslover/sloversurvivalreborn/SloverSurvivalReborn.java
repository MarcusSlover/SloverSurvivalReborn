package me.marcusslover.sloversurvivalreborn;

import me.marcusslover.sloversurvivalreborn.bank.Bank;
import me.marcusslover.sloversurvivalreborn.code.CodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.command.CommandHandler;
import me.marcusslover.sloversurvivalreborn.code.data.FileDataHandler;
import me.marcusslover.sloversurvivalreborn.code.data.IFileData;
import me.marcusslover.sloversurvivalreborn.listener.ChatListener;
import me.marcusslover.sloversurvivalreborn.listener.ServerListener;
import me.marcusslover.sloversurvivalreborn.rank.RankHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class SloverSurvivalReborn extends JavaPlugin {
    private static SloverSurvivalReborn instance;

    @Override
    public void onEnable() {
        instance = this;
        log().info("Enabling the plugin...");

        log().info("Checking for the data folder...");
        if (!getDataFolder().exists()) {
            boolean mkdirs = getDataFolder().mkdirs();
            if (mkdirs) {
                log().info("File successfully created!");
            }
        }

        log().info("Initializing the services...");
        CodeInitializer codeInitializer = new CodeInitializer();
        codeInitializer.add(new CommandHandler());
        codeInitializer.add(new RankHandler());
        codeInitializer.add(new FileDataHandler());
        codeInitializer.add(new ServerListener());
        codeInitializer.add(new ChatListener());
        codeInitializer.add(new Bank());

        log().info("Initialization done.");
    }

    @Override
    public void onDisable() {
        log().info("Disabling file handlers...");
        FileDataHandler fileDataHandler = CodeInitializer.find(FileDataHandler.class);
        if (fileDataHandler != null) {
            for (IFileData<?> iFileData : fileDataHandler.getRegistered()) {
                try {
                    iFileData.onDisable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            log().info("Phase of disabling file handlers - done.");
        } else {
            log().warning("An error occurred while disabling file handlers.");
        }

        log().info("The plugin was disabled!");
    }

    public static Logger log() {
        return getInstance().getLogger();
    }

    public static SloverSurvivalReborn getInstance() {
        return instance;
    }
}
