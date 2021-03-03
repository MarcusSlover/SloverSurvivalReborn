package me.marcusslover.sloversurvivalreborn.utils;

import com.google.gson.JsonParser;
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

    public static final JsonParser JSON_PARSER = new JsonParser();

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ColorUtil.toColor(message));
    }
}
