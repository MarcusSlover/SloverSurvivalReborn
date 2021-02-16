package me.marcusslover.sloversurvivalreborn.utils;

import me.marcusslover.sloversurvivalreborn.SloverSurvivalReborn;
import org.apache.commons.lang.WordUtils;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class API {
    public static String toFancyTitle(String string) {
        return WordUtils.capitalize(string);
    }

    public static Logger getLogger() {
        return SloverSurvivalReborn.log();
    }

    public static final String SUCCESS = "%rgb(100,176,28)➤ %rgb(129,216,46)Success&8: &7";
    public static final String INFO = "%rgb(4,165,186)➤ %rgb(54,183,199)Info&8: &7";
    public static final String ERROR = "%rgb(176,31,31)➤ %rgb(251,48,48)Error&8: &7";

    public static void sendSuccess(CommandSender sender, String message) {
        sender.sendMessage(Colors.toColor(SUCCESS + message));
    }

    public static void sendInformation(CommandSender sender, String message) {
        sender.sendMessage(Colors.toColor(INFO + message));
    }

    public static void sendError(CommandSender sender, String message) {
        sender.sendMessage(Colors.toColor(ERROR + message));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(Colors.toColor(message));
    }
}
