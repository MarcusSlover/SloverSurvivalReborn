package me.marcusslover.sloversurvivalreborn.code.task;

import me.marcusslover.sloversurvivalreborn.SloverSurvivalReborn;
import org.bukkit.Bukkit;

public interface ITask {
    static void applyAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(SloverSurvivalReborn.getInstance(), runnable);
    }

    static void applySync(Runnable runnable) {
        Bukkit.getScheduler().runTask(SloverSurvivalReborn.getInstance(), runnable);
    }
}
