package me.marcusslover.sloversurvivalreborn.listener;

import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.PatchVersion;
import me.marcusslover.sloversurvivalreborn.code.task.ITask;
import me.marcusslover.sloversurvivalreborn.user.UserFileData;
import me.marcusslover.sloversurvivalreborn.utils.RankUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@PatchVersion(version = "2.0.0")
public class ServerListener implements ICodeInitializer, Listener {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent loginEvent) {
        UUID uniqueId = loginEvent.getUniqueId();
        if (loginEvent.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            return;
        }
        UserFileData instance = UserFileData.getInstance();
        String key = uniqueId.toString();
        instance.read(key);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();
        String key = uniqueId.toString();
        UserFileData userFileData = UserFileData.getInstance();

        EXECUTOR_SERVICE.submit(() -> {
            while (true) {
                if (!player.isOnline()) {
                    return false;
                }

                if (userFileData.getMap().containsKey(key)) {
                    ITask.applySync(() -> RankUtil.updateScoreboard(player));
                    return true;
                }
            }
        });

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        UserFileData instance = UserFileData.getInstance();
        String key = uniqueId.toString();

        ITask.applyAsync(() -> {
            instance.save(key);
            instance.getMap().remove(key);
        });

    }

}
