package me.marcusslover.sloversurvivalreborn.command;

import me.marcusslover.sloversurvivalreborn.code.command.Command;
import me.marcusslover.sloversurvivalreborn.code.command.PlayerCommand;
import me.marcusslover.sloversurvivalreborn.code.event.IMenu;
import me.marcusslover.sloversurvivalreborn.code.event.Menu;
import me.marcusslover.sloversurvivalreborn.item.ItemBuilder;
import me.marcusslover.sloversurvivalreborn.item.MenuBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Command(name = "bank")
@Menu(name = "Bank Menu")
public class BankCommand implements PlayerCommand, IMenu {
    public final static ItemStack ITEM_HEADER;
    public final static ItemStack ITEM_BORDER;
    public final static ItemStack ITEM_MANAGE_ACCOUNTS;
    public final static ItemStack ITEM_STATISTICS;

    static {
        ITEM_HEADER = new ItemBuilder()
                .setMaterial(Material.GOLD_INGOT)
                .withName("%hex(edbe13)Bank Menu")
                .withAmount(1)
                .withLore(
                        "%hex(13baed)Through the Bank menu you can do things such as:",
                        "%hex(13baed)- Transfer doubloons from and to accounts",
                        "%hex(13baed)- Convert diamonds to doubloons",
                        "%hex(13baed)- View how much tax you have to pay"
                )
                .build();

        ITEM_BORDER = new ItemBuilder()
                .setMaterial(Material.YELLOW_STAINED_GLASS_PANE)
                .withName("&r")
                .withAmount(1)
                .build();

        ITEM_MANAGE_ACCOUNTS = new ItemBuilder()
                .setMaterial(Material.ENDER_CHEST)
                .withName("%hex(3049b0)Manage Accounts")
                .withAmount(1)
                .withLore(
                        "%hex(13baed)- Create joint accounts with others",
                        "%hex(13baed)- Transfer doubloons between accounts",
                        "%hex(13baed)- Convert diamonds to doubloons and vice versa.",
                        "%hex(13baed)- View taxes."
                )
                .build();

        ITEM_STATISTICS = new ItemBuilder()
                .setMaterial(Material.BARREL)
                .withName("%hex(f5b802)Statistics")
                .withAmount(1)
                .withLore(
                        "%hex(13baed)- View top player balances",
                        "%hex(13baed)- View taxes paid over time"
                )
                .build();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        this.openMenu(player);
    }

    private void openMenu(Player player) {
        MenuBuilder builder = new MenuBuilder()
                .withName("Bank Menu")
                .withSize(5 * 9)
                .withItem(4, ITEM_HEADER)
                .withItem(18 + 3, ITEM_MANAGE_ACCOUNTS)
                .withItem(18 + 5, ITEM_STATISTICS)
                .withBorder(ITEM_BORDER, false);

        Inventory inventory = builder.build();
        player.openInventory(inventory);
    }

    @Override
    public void onClick(Player player, InventoryClickEvent event) {


        event.setCancelled(true);
    }
}
