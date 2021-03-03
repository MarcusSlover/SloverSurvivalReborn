package me.marcusslover.sloversurvivalreborn.warp;

import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.PatchVersion;
import me.marcusslover.sloversurvivalreborn.code.task.ITask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@PatchVersion(version = "1.0.1")
public class WarpManager implements ICodeInitializer {
    private static WarpManager instance;

    public WarpManager() {
        instance = this;
    }

    @Override
    public void initialize() {
        this.loadWarps(warps -> {
            // possibly log it?
            initLog(String.format("%s warps", warps.size()));
        });
    }

    private void loadWarps(Consumer<List<Warp>> consumer) {
        // Source of all the data
        WarpFileData warpFileData = WarpFileData.getInstance();
        long now = System.currentTimeMillis();
        log("Loading warps...");

        // Run async
        ITask.applyAsync(() -> {
            List<Warp> warps = new ArrayList<>();
            File dataFolder = WarpFileData.getInstance().getDataFolder();
            if (dataFolder.isDirectory()) {

                // All files in the directory
                File[] files = dataFolder.listFiles();
                for (File file : files) {
                    if (file != null) {
                        String name = file.getName();

                        // Files can end with .json for example
                        if (name.contains(".")) {
                            String[] split = name.split("\\.");
                            name = split[0];
                        }

                        // Read the file
                        warpFileData.read(name);
                        warps.add(warpFileData.getMap().get(name));
                    }
                }
            }
            long time = (System.currentTimeMillis() - now) / 1000;
            log(String.format("Loaded in %s seconds!", time));

            // Run sync
            ITask.applySync(() -> {
                // Escape with sync call
                consumer.accept(warps);
            });
        });

    }

    public static WarpManager getInstance() {
        return instance;
    }
}
