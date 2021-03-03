package me.marcusslover.sloversurvivalreborn.rank;

import me.marcusslover.sloversurvivalreborn.code.CodeInitializer;
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

import java.util.ArrayList;
import java.util.List;

@Menu(name = "Rank Shop")
@Command(name = "rank", aliases = {"ranks"})
public class RankCommand implements PlayerCommand, IMenu {
    private final static ItemStack ITEM_BORDER;

    static {
        ITEM_BORDER = new ItemBuilder()
                .withMaterial(Material.ORANGE_STAINED_GLASS_PANE)
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
        RankHandler rankHandler = CodeInitializer.find(RankHandler.class);

        int index = 10;
        for (Rank rank : rankHandler.getRegistered()) {
            if (rank instanceof SpecialRank) {
                SpecialRank specialRank = (SpecialRank) rank;

                Material material = specialRank.getMaterial();
                String prefix = specialRank.getPrefix();
                String color = specialRank.getColor();
                String namePreview = prefix + " " + color + player.getName();

                ItemBuilder itemBuilder = new ItemBuilder()
                        .withMaterial(material)
                        .withAmount(1)
                        .withName(prefix + specialRank.getName());

                List<String> lore = new ArrayList<>();
                lore.add("&7Preview:");
                lore.add(namePreview);
                lore.add("");
                itemBuilder.withLore(lore);
                menuBuilder.withItem(index, itemBuilder.build());

                index++;
            }
        }

        Inventory build = menuBuilder.build();
        player.openInventory(build);
    }


    @Override
    public void onClick(Player player, InventoryClickEvent event) {

    }
}
