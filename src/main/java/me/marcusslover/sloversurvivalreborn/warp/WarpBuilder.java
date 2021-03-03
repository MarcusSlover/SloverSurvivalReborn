package me.marcusslover.sloversurvivalreborn.warp;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.utils.IBuilder;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.UUID;

public class WarpBuilder implements IBuilder<Warp> {
    private Location location;
    private UUID owner;
    private String name;
    private Material icon;
    private Warp.Type type;

    public WarpBuilder() {}

    public WarpBuilder(Location location) {
        this.location = location;
    }

    public WarpBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public WarpBuilder withIcon(Material icon) {
        this.icon = icon;
        return this;
    }

    public WarpBuilder withOwner(UUID owner) {
        this.owner = owner;
        return this;
    }

    public WarpBuilder withType(Warp.Type type) {
        this.type = type;
        return this;
    }

    public WarpBuilder withLocation(Location location) {
        this.location = location;
        return this;
    }

    @Override
    public Warp build() {
        Warp warp = new Warp(new JsonObject());
        warp.setLocation(location);
        warp.setOwner(owner);
        warp.setWarpName(name);
        warp.setWarpIcon(icon);
        warp.setType(type);
        return warp;
    }
}
