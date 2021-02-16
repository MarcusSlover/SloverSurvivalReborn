package me.marcusslover.sloversurvivalreborn.item;

import me.marcusslover.sloversurvivalreborn.utils.Colors;
import me.marcusslover.sloversurvivalreborn.code.IBuilder;
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

    @Override
    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (name != null || lore != null) {
            if (name != null) {
                itemMeta.setDisplayName(Colors.toColor(name));
            }

            if (lore != null) {
                List<String> itemLore = lore.stream().map(Colors::toColor).collect(Collectors.toList());
                itemMeta.setLore(itemLore);
            }

            itemStack.setItemMeta(itemMeta);
        }

        return itemStack;
    }
}
