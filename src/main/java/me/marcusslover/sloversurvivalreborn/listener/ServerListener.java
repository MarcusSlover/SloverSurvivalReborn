package me.marcusslover.sloversurvivalreborn.listener;

import me.marcusslover.sloversurvivalreborn.code.CodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.task.ITask;
import me.marcusslover.sloversurvivalreborn.rank.Rank;
import me.marcusslover.sloversurvivalreborn.rank.RankHandler;
import me.marcusslover.sloversurvivalreborn.user.User;
import me.marcusslover.sloversurvivalreborn.user.UserFileData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.UUID;

public class ServerListener implements ICodeInitializer, Listener {

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

        RankHandler rankHandler = CodeInitializer.find(RankHandler.class);
        if (rankHandler != null) {
            Scoreboard mainScoreboard = rankHandler.getMainScoreboard();

            UserFileData instance = UserFileData.getInstance();
            Map<String, User> map = instance.getMap();

            User user = map.get(key);
            Rank rank = user.getRank();
            String name = rank.getName();

            for (Team team : mainScoreboard.getTeams()) {
                if (team.getName().contains(name)) {
                    if (team.hasEntry(player.getName())) {
                        break;
                    }
                    team.addEntry(player.getName());
                    break;
                }
            }
            player.setScoreboard(mainScoreboard);
        }
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
