package me.marcusslover.sloversurvivalreborn.utils;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ChatUtil {
    public static final String SUCCESS = "%hex(#6BFF33)⬛ %hex(#CBCBCB)";
    public static final String INFO = "%hex(#FF3333)⬛ %hex(#CBCBCB)";
    public static final String ERROR = "%hex(#33C1FF)⬛ %hex(#CBCBCB)";

    public static void success(Player player, String message) {
        player.sendMessage(ColorUtil.toColor(SUCCESS + message));
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 2.0f);
    }

    public static void info(Player player, String message) {
        player.sendMessage(ColorUtil.toColor(INFO + message));
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 1.0f, 2.0f);
    }

    public static void error(Player player, String message) {
        player.sendMessage(ColorUtil.toColor(ERROR + message));
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0f, 2.0f);
    }
}
