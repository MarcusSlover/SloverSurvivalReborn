package me.marcusslover.sloversurvivalreborn.command;

import me.marcusslover.sloversurvivalreborn.code.command.Command;
import me.marcusslover.sloversurvivalreborn.code.command.PlayerCommand;
import me.marcusslover.sloversurvivalreborn.code.event.IMenu;
import me.marcusslover.sloversurvivalreborn.item.ItemBuilder;
import me.marcusslover.sloversurvivalreborn.item.MenuBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Command(name = "warp")
public class WarpCommand implements PlayerCommand, IMenu {
    private final static ItemStack ITEM_BORDER;

    static {
        ITEM_BORDER = new ItemBuilder()
                .setMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                .withName("&r")
                .withAmount(1)
                .build();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        this.openMenu(player);
    }

    private void openMenu(Player player) {
        MenuBuilder builder = new MenuBuilder()
                .withName("Warp Menu")
                .withSize(6 * 9)
                .withBorder(ITEM_BORDER, false);

        Inventory inventory = builder.build();
        player.openInventory(inventory);
    }

    @Override
    public void onClick(Player player, InventoryClickEvent event) {

    }
}
