package me.marcusslover.sloversurvivalreborn.listener;

import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.data.PlayerFileData;
import me.marcusslover.sloversurvivalreborn.code.task.ITask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class ServerListener implements ICodeInitializer, Listener {

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent loginEvent) {
        UUID uniqueId = loginEvent.getUniqueId();
        if (loginEvent.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            return;
        }
        PlayerFileData instance = PlayerFileData.getInstance();
        String key = uniqueId.toString();
        instance.read(key);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        PlayerFileData instance = PlayerFileData.getInstance();
        String key = uniqueId.toString();

        ITask.applyAsync(() -> instance.save(key));

    }

}
