package me.marcusslover.sloversurvivalreborn.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class FurnaceRecipe extends CustomRecipe {
    private CraftingRecipe.CraftingRecipeChoice choice;
    private float experience = 0;
    private int time = 1;
    public FurnaceRecipe(String name, ItemStack result, ItemStack... inputChoices) {
        super(name, result);
        this.choice = new CraftingRecipe.CraftingRecipeChoice(1, Arrays.asList(inputChoices));
    }

    public void setTime(int time) {
        this.time = time;
    }
    public void setExperience(float experience) {
        this.experience = experience;
    }

    @Override
    @NotNull Recipe getRecipe() {
        return new org.bukkit.inventory.FurnaceRecipe(this.getName(), this.getResult(), this.choice, this.experience, this.time);
    }
}
