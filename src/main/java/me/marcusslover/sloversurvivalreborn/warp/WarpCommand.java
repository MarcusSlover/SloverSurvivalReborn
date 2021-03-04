package me.marcusslover.sloversurvivalreborn.warp;

import me.marcusslover.sloversurvivalreborn.code.command.Command;
import me.marcusslover.sloversurvivalreborn.code.command.PlayerCommand;
import me.marcusslover.sloversurvivalreborn.code.event.IMenu;
import me.marcusslover.sloversurvivalreborn.item.ItemBuilder;
import me.marcusslover.sloversurvivalreborn.item.MenuBuilder;
import me.marcusslover.sloversurvivalreborn.utils.ColorUtil;
import me.marcusslover.sloversurvivalreborn.utils.ItemUtil;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.marcusslover.sloversurvivalreborn.utils.ColorUtil.LIGHT_GRAY;

@Command(name = "warp")
public class WarpCommand implements PlayerCommand, IMenu {
    private final static ItemStack ITEM_BORDER;
    private final static ItemStack ITEM_MANAGE_WARPS;
    private final static ItemStack ITEM_EXPLORE_WARPS;

    static {
        ITEM_BORDER = new ItemBuilder()
                .withMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                .withName("&r")
                .withAmount(1)
                .build();

        ITEM_MANAGE_WARPS = new ItemBuilder()
                .withMaterial(Material.CARTOGRAPHY_TABLE)
                .withName(ColorUtil.ORANGE + "Your Warps")
                .withLore(
                        LIGHT_GRAY + "- View your warps.",
                        LIGHT_GRAY + "- Create new warp locations.",
                        LIGHT_GRAY + "- Manage privacy of existing warps.",
                        LIGHT_GRAY + "- Delete existing warps."
                )
                .withTag("warp", "your_warps")
                .build();

        ITEM_EXPLORE_WARPS = new ItemBuilder()
                .withMaterial(Material.SOUL_CAMPFIRE)
                .withName(ColorUtil.BLUE + "Explore Warps")
                .withLore(
                        LIGHT_GRAY + "- View all allowed warps.",
                        LIGHT_GRAY + "- Search for warps."
                )
                .withTag("warp", "explore_warps")
                .build();
    }

    @Override
    public void onCommand(Player player, String[] args) {
        if (args.length == 0) {
            this.openMenu(player);
        }
    }

    private void openMenu(Player player) {
        MenuBuilder builder = new MenuBuilder()
                .withName("Warp Menu")
                .withSize(6 * 9)
                .withBorder(ITEM_BORDER, false);

        builder.withItem(11, ITEM_MANAGE_WARPS);
        builder.withItem(15, ITEM_EXPLORE_WARPS);

        Inventory inventory = builder.build();
        player.openInventory(inventory);
    }

    @Override
    public void onClick(Player player, InventoryClickEvent event) {
        event.setCancelled(true);

        ItemStack item = event.getCurrentItem();
        if (!ItemUtil.isValid(item)) return;

        if (ItemUtil.hasTag(item, "warp")) {
            NBTTagCompound tag = ItemUtil.getTag(item);
            String warp = tag.getString("warp");
            if (warp.equals("your_warps")) {

            }
        }
    }
}
