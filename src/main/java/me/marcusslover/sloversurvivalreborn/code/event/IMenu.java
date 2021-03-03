package me.marcusslover.sloversurvivalreborn.code.event;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;
import java.util.Map;

public interface IMenu {

    void onClick(Player player, InventoryClickEvent event);

    default Menu getMenuAnnotation() {
        Menu data = IMenu.Cache.getAnnot(this.getClass());
        if (data == null)
            data = this.getClass().getDeclaredAnnotation(Menu.class);
        if (data == null)
            throw new RuntimeException("IMenu class doesn't have @Menu annotation");
        return data;
    }

    @SuppressWarnings("rawtypes")
    class Cache {
        private static final Map<Class<? extends IMenu>, Menu> cache = new HashMap<>();

        static Menu getAnnot(Class<? extends IMenu> clz) {
            return cache.get(clz);
        }
        static void setAnnot(Class<? extends IMenu> clz, Menu data) {
            cache.put(clz, data);
        }
    }
}
