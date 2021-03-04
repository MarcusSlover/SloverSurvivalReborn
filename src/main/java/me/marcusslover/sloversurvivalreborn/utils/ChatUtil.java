package me.marcusslover.sloversurvivalreborn.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import static me.marcusslover.sloversurvivalreborn.utils.ColorUtil.LIGHT_GRAY;

public class ChatUtil {
    public static final String SUCCESS = "%hex(#6BFF33)⬛ "+ LIGHT_GRAY;
    public static final String INFO = "%hex(#FF3333)⬛ "+ LIGHT_GRAY;
    public static final String ERROR = "%hex(#33C1FF)⬛ "+ LIGHT_GRAY;

    public static void success(Player player, String message) {
        player.sendMessage(ColorUtil.toColor(SUCCESS + message));
        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1.0f, 2.0f);
    }

    public static void success(Player player, String message, String hover, String command) {
        TextComponent textComponent = new TextComponent(ColorUtil.toColor(message));
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ColorUtil.toColor(hover)).create()));
        player.spigot().sendMessage(textComponent);
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
