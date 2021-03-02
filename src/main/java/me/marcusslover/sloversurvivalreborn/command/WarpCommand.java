package me.marcusslover.sloversurvivalreborn.command;

import me.marcusslover.sloversurvivalreborn.code.command.Command;
import me.marcusslover.sloversurvivalreborn.code.command.PlayerCommand;
import me.marcusslover.sloversurvivalreborn.code.event.IMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

@Command(name = "warp")
public class WarpCommand implements PlayerCommand, IMenu {

    @Override
    public void onCommand(Player player, String[] args) {
        this.openMenu(player);
    }

    private void openMenu(Player player) {

    }

    @Override
    public void onClick(Player player, InventoryClickEvent event) {

    }
}
