package me.marcusslover.sloversurvivalreborn.rank;

import org.bukkit.Material;

public class SpecialRank extends Rank {
    private final Material material;

    public SpecialRank(Material material, String name, String prefix, String color) {
        super(name, prefix, color);
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }
}
