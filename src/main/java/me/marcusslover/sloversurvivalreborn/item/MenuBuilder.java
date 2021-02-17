package me.marcusslover.sloversurvivalreborn.item;

import me.marcusslover.sloversurvivalreborn.utils.IBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MenuBuilder implements IBuilder<Inventory> {
    int size = 0;
    String name = null;

    Map<Integer, ItemStack> items = null;

    public MenuBuilder withSize(int size) {
        this.size = size;
        return this;
    }

    public MenuBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MenuBuilder withItem(int slot, ItemStack itemStack) {
        if (items == null) {
            items = new HashMap<>();
        }
        items.put(slot, itemStack);
        return this;
    }

    @Override
    public Inventory build() {
        Inventory inventory = Bukkit.createInventory(null, size, name);
        items.forEach(inventory::setItem);

        return inventory;
    }
}
