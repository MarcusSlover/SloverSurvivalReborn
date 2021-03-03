package me.marcusslover.sloversurvivalreborn.listener;

import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.PatchVersion;
import me.marcusslover.sloversurvivalreborn.rank.Rank;
import me.marcusslover.sloversurvivalreborn.user.User;
import me.marcusslover.sloversurvivalreborn.user.UserFileData;
import me.marcusslover.sloversurvivalreborn.utils.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Map;
import java.util.UUID;

@PatchVersion
public class ChatListener implements ICodeInitializer, Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();
        String key = uniqueId.toString();

        Map<String, User> map = UserFileData.getInstance().getMap();
        if (map.containsKey(key)) {
            User user = map.get(key);
            Rank rank = user.getRank();

            String prefix = rank.getPrefix();
            String color = rank.getColor();

            event.setFormat(ColorUtil.toColor(prefix + "&r " + color + "%s&r:") + " %s");
        } else {
            event.setCancelled(true);
        }
    }
}
