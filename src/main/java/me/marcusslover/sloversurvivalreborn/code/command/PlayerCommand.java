package me.marcusslover.sloversurvivalreborn.code.command;

import org.bukkit.entity.Player;

public interface PlayerCommand extends SloverCommand {
    void onCommand(Player player, String[] args);


}
