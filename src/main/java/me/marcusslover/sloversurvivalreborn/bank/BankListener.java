package me.marcusslover.sloversurvivalreborn.bank;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class BankListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.DIAMOND_ORE)
            return;
        if (!event.isDropItems())
            return;
        switch (event.getPlayer().getGameMode()) {
            case CREATIVE:
            case SPECTATOR:
                return;
            default:
                break;
        }

        ItemStack pickaxe = event.getPlayer().getInventory().getItemInMainHand();
        if (pickaxe.containsEnchantment(Enchantment.SILK_TOUCH))
            return;
        event.setCancelled(true);

        long diamonds = 0;
        Collection<ItemStack> drops = event.getBlock().getDrops(pickaxe, event.getPlayer());
        World world = event.getBlock().getWorld();
        event.getBlock().setType(Material.AIR);
        for (ItemStack stack : drops) {
            if (stack.getType() == Material.DIAMOND) {
                diamonds = stack.getAmount();
            }
            world.dropItemNaturally(event.getBlock().getLocation(), stack);
        }

        Bank.setDiamondCount(Bank.getDiamondCount() + diamonds);
    }
}
