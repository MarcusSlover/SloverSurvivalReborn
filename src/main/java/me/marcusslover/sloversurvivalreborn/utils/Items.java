package me.marcusslover.sloversurvivalreborn.utils;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

    public static boolean isNamed(ItemStack item, String name) {
        if (!isValid(item)) return false;
        if (!item.hasItemMeta() && item.getItemMeta() == null) return false;

        ItemMeta itemMeta = item.getItemMeta();
        if (!itemMeta.hasDisplayName()) {
            return false;
        }
        String itemName = itemMeta.getDisplayName();
        return itemName.equalsIgnoreCase(Colors.toColor(name));
    }

    public static boolean hasTag(ItemStack item, String key) {
        if (!isValid(item)) return false;
        net.minecraft.server.v1_16_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = itemStack.getTag();
        if (tag == null) return false;

        return tag.hasKey(key);
    }

    public static NBTTagCompound getTag(ItemStack item) {
        if (!isValid(item)) return null;
        net.minecraft.server.v1_16_R3.ItemStack itemStack = CraftItemStack.asNMSCopy(item);
        return itemStack.getTag();
    }

    public static boolean isValid(ItemStack item) {
        return item != null && item.getType() != Material.AIR;
    }
}
