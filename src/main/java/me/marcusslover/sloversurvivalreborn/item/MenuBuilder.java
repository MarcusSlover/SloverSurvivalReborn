package me.marcusslover.sloversurvivalreborn.item;

import me.marcusslover.sloversurvivalreborn.utils.IBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MenuBuilder implements IBuilder<Inventory> {
    public static final List<Integer> menus = new ArrayList<>();
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


    public MenuBuilder withBorder(ItemStack item, boolean override) {
        int height = getHeight();
        // left & right sides
        for (int i = 0; i < height; i++) {
            int index = i * 9;
            if (override || !hasItemAtIndex(index))
                withItem(index, item);
            if (override || !hasItemAtIndex(index + 8))
                withItem(index + 8, item);
        }
        int heightOffset = (height * 9);
        // top & bottom sides
        for (int i = 1; i < 8; i++) { // small optimisation
            if (override || !hasItemAtIndex(i))
                withItem(i, item);
            if (override || !hasItemAtIndex(i + heightOffset))
            withItem(i + heightOffset, item);
        }

        return this;
    }

    @Override
    public Inventory build() {
        Inventory inventory = Bukkit.createInventory(null, size, name);
        items.forEach(inventory::setItem);
        int hashcode = Arrays.hashCode(inventory.getStorageContents());
        if (!menus.contains(hashcode)) menus.add(hashcode);
        return inventory;
    }

    private int getHeight() {
        return (size - (size % 9)) / 9;
    }

    public boolean hasItemAtIndex(int index) {
        return items.containsKey(index);
    }
}
