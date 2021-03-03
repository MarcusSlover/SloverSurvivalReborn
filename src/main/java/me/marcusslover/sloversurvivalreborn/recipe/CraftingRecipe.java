package me.marcusslover.sloversurvivalreborn.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class CraftingRecipe extends CustomRecipe {
    public CraftingRecipe(String name, ItemStack result) {
        super(name, result);
    }

    public static class CraftingRecipeChoice implements RecipeChoice {
        List<ItemStack> choices;
        int count;

        public CraftingRecipeChoice(int count, @NotNull Collection<ItemStack> choices) {
            assert count > 0 : "Count must be greater than 0.";
            this.count = count;
            assert !choices.isEmpty() : "Choices cannot be empty.";
            this.choices = new ArrayList<>(choices);
        }

        @NotNull
        @Override
        public ItemStack getItemStack() {
            return this.choices.get(0);
        }

        @NotNull
        @Override
        public RecipeChoice clone() {
            try {
                CraftingRecipeChoice clone = (CraftingRecipeChoice) super.clone();
                return new CraftingRecipeChoice(this.count, new ArrayList<>(this.choices));
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public boolean test(@NotNull ItemStack itemStack) {
            for (ItemStack item : this.choices) {
                if (item.isSimilar(itemStack) || itemStack.isSimilar(item))
                    return true;
            }
            return false;
        }
    }
}
