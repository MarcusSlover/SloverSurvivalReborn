package me.marcusslover.sloversurvivalreborn.recipe;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class ShapedCraftingRecipe extends CraftingRecipe {
    private CraftingRecipeChoice[] choices;
    public ShapedCraftingRecipe(String name, ItemStack result) {
        super(name, result);
        this.choices = new CraftingRecipeChoice[9];
    }

    public void setItem(int index, ItemStack... itemChoices) {
        assert index < 9 && index >= 0 : "Item index must be between 0 (inclusive) and 9 (exclusive)";
        choices[index] = new CraftingRecipeChoice(1, Arrays.asList(itemChoices));
    }

    @Override
    @NotNull
    Recipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(this.getName(), this.result);
        String[] lines = new String[]{"", "", ""};
        for (int i = 0; i < choices.length; i++) {
            CraftingRecipeChoice choice = choices[i];
            int line = Math.floorDiv(i, 3);
            if (choice.getItemStack().getType() != Material.AIR) {
                recipe.setIngredient((char) ('a' + i), choice);
                lines[line] += (char) ('a' + i);
            }
        }
        return recipe;
    }
}
