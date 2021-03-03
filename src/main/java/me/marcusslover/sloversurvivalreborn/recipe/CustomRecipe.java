package me.marcusslover.sloversurvivalreborn.recipe;

import me.marcusslover.sloversurvivalreborn.SloverSurvivalReborn;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

public abstract class CustomRecipe {
    protected ItemStack result;
    protected String name;
    public CustomRecipe(String name, ItemStack result) {
        this.result = result;
        this.name = name;
    }
    NamespacedKey getName() {
        return new NamespacedKey(SloverSurvivalReborn.getInstance(), name);
    }
    ItemStack getResult() {
        return result;
    }
    @NotNull
    abstract Recipe getRecipe();
}
