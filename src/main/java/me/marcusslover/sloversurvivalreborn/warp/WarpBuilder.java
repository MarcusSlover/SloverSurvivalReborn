package me.marcusslover.sloversurvivalreborn.warp;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.utils.IBuilder;
import org.bukkit.Location;

import java.util.UUID;

public class WarpBuilder implements IBuilder<Warp> {
    private Location location;
    private UUID owner;

    public WarpBuilder() {}

    public WarpBuilder(Location location) {
        this.location = location;
    }

    public WarpBuilder withOwner(UUID owner) {
        this.owner = owner;
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
        return warp;
    }
}
