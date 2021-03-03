package me.marcusslover.sloversurvivalreborn.rank;

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

@Menu(name = "Rank Shop")
@Command(name = "rank", aliases = {"ranks"})
public class RankCommand implements PlayerCommand, IMenu {
    private final static ItemStack ITEM_BORDER;

    static {
        ITEM_BORDER = new ItemBuilder()
                .setMaterial(Material.ORANGE_STAINED_GLASS_PANE)
                .withName("&r")
                .withAmount(1)
                .build();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        this.openMenu(player);
    }

    public void openMenu(Player player) {
        MenuBuilder menuBuilder = new MenuBuilder()
                .withName(this.getMenuAnnotation().name())
                .withBorder(ITEM_BORDER, false);

        Inventory build = menuBuilder.build();
        player.openInventory(build);
    }


    @Override
    public void onClick(Player player, InventoryClickEvent event) {

    }
}
