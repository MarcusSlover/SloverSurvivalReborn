package me.marcusslover.sloversurvivalreborn.code.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface IMenu {

    void onClick(Player player, InventoryClickEvent event);
}
