package me.marcusslover.sloversurvivalreborn.command;

import me.marcusslover.sloversurvivalreborn.code.command.Command;
import me.marcusslover.sloversurvivalreborn.code.command.PlayerCommand;
import me.marcusslover.sloversurvivalreborn.code.event.IMenu;
import me.marcusslover.sloversurvivalreborn.code.event.Menu;
import me.marcusslover.sloversurvivalreborn.item.ItemBuilder;
import me.marcusslover.sloversurvivalreborn.item.MenuBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Command(name = "bank")
@Menu(name = "Bank Menu")
public class BankCommand implements PlayerCommand, IMenu {
    public final static ItemStack BANK_HEADER;

    static {
        BANK_HEADER = new ItemBuilder()
                .withName("%hex(edbe13)")
                .withAmount(1)
                .withLore(
                        "%hex(13baed)Through the Bank menu you can do things such as:",
                        "%hex(13baed)- Transfer doubloons from and to accounts",
                        "%hex(13baed)- Convert diamonds to doubloons",
                        "%hex(13baed)- View how much tax you have to pay"
                ).build();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        this.openMenu(player);
    }

    private void openMenu(Player player) {
        Inventory inventory = new MenuBuilder()
                .withName("Bank Menu")
                .withSize(5 * 9)
                .build();
        inventory.setItem(4, BANK_HEADER);
        player.openInventory(inventory);
    }

    @Override
    public void onClick(Player player, InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
