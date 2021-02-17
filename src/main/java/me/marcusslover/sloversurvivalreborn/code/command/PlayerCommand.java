package me.marcusslover.sloversurvivalreborn.code.command;

import org.bukkit.entity.Player;

public interface PlayerCommand extends ICommand {
    void onCommand(Player player, String[] args);


}
