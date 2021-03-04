package me.marcusslover.sloversurvivalreborn.code.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;

public interface IMenu {
    default int[] getSlots(int rows) {
        int[] ints = Cache.getCachedIntegers(rows);
        if (IMenu.Cache.getCachedIntegers(rows) == null) {
            ints = new int[rows * 7];

            int index = 0;
            for (int i = 0; i < rows; i++) {
                int slot = 10 + (9 * i);

                for (int j = 0; j < 7; j++) {
                    ints[index] = slot + j;
                    index += 1;
                }
            }
            IMenu.Cache.setCachedIntegers(rows, ints);
        }
        return ints;
    }

    void onClick(Player player, InventoryClickEvent event);

    default Menu getMenuAnnotation() {
        Menu data = IMenu.Cache.getAnnot(this.getClass());
        if (data == null) {
            data = this.getClass().getDeclaredAnnotation(Menu.class);
            IMenu.Cache.setAnnot(this.getClass(), data);
        }
        if (data == null)
            throw new RuntimeException("IMenu class doesn't have @Menu annotation");

        return data;
    }

    @SuppressWarnings("rawtypes")
    class Cache {
        private static final Map<Integer, int[]> rowsCache = new HashMap<>();
        private static final Map<Class<? extends IMenu>, Menu> cache = new HashMap<>();

        static int[] getCachedIntegers(int rows) {
            return rowsCache.get(rows);
        }

        static void setCachedIntegers(int rows, int[] ints) {
            rowsCache.put(rows, ints);
        }

        static Menu getAnnot(Class<? extends IMenu> clz) {
            return cache.get(clz);
        }
        static void setAnnot(Class<? extends IMenu> clz, Menu data) {
            cache.put(clz, data);
        }
    }
}
