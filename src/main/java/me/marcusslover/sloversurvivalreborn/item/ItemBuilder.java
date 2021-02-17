package me.marcusslover.sloversurvivalreborn.item;

import me.marcusslover.sloversurvivalreborn.utils.ColorUtil;
import me.marcusslover.sloversurvivalreborn.utils.IBuilder;
import me.marcusslover.sloversurvivalreborn.utils.ItemUtil;
import net.minecraft.server.v1_16_R3.NBTBase;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder implements IBuilder<ItemStack> {
    Material material = Material.AIR;
    int amount = 0;
    String name = null;
    List<String> lore = null;
    NBTTagCompound nbtTagCompound = new NBTTagCompound();

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder addLore(String lore) {
        if (this.lore == null) {
            this.lore = new ArrayList<>();
        }
        this.lore.add(lore);
        return this;
    }

    public ItemBuilder withLore(String... lore) {
        return this.withLore(Arrays.asList(lore));
    }

    public ItemBuilder withLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder withTag(String key, String value) {
        this.nbtTagCompound.setString(key, value);
        return this;
    }

    public ItemBuilder withTag(String key, int value) {
        this.nbtTagCompound.setInt(key, value);
        return this;
    }

    public ItemBuilder withTag(String key, double value) {
        this.nbtTagCompound.setDouble(key, value);
        return this;
    }

    public ItemBuilder withTag(String key, boolean value) {
        this.nbtTagCompound.setBoolean(key, value);
        return this;
    }

    @Override
    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (name != null || lore != null) {
            if (name != null) {
                itemMeta.setDisplayName(ColorUtil.toColor(name));
            }

            if (lore != null) {
                List<String> itemLore = lore.stream().map(ColorUtil::toColor).collect(Collectors.toList());
                itemMeta.setLore(itemLore);
            }

            itemStack.setItemMeta(itemMeta);
        }

        if (!nbtTagCompound.isEmpty()) {
            NBTTagCompound tag = ItemUtil.getTag(itemStack);
            if (tag == null) {
                tag = new NBTTagCompound();
            }

            for (String key : nbtTagCompound.getKeys()) {
                NBTBase nbtBase = nbtTagCompound.get(key);
                tag.set(key, nbtBase);
            }
        }


        return itemStack;
    }
}
