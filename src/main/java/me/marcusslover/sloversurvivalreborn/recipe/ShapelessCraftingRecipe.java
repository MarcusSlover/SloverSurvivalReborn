package me.marcusslover.sloversurvivalreborn.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShapelessCraftingRecipe extends CraftingRecipe {
    private List<CraftingRecipeChoice> choices;
    public ShapelessCraftingRecipe(String name, ItemStack result) {
        super(name, result);
        choices = new ArrayList<>();
    }

    public void addChoice(int amount, ItemStack... items) {
        this.choices.add(new CraftingRecipeChoice(amount, Arrays.asList(items)));
    }

    @Override
    @NotNull
    Recipe getRecipe() {
        ShapelessRecipe recipe = new ShapelessRecipe(this.getName(), getResult());
        for (RecipeChoice choice : choices) {
            recipe.addIngredient(choice);
        }
        return recipe;
    }
}
