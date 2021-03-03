package me.marcusslover.sloversurvivalreborn.warp;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.utils.JsonModel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Warp extends JsonModel {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private String name;
    private List<String> whitelist;
    private Type type;
    private UUID owner;

    public Warp(JsonObject jsonObject) {
        super(jsonObject);
        this.init();
    }

    private void init() {
        this.owner = getUUID("owner", null);
        this.x = getDouble("x", 0.0d);
        this.y = getDouble("y", 0.0d);
        this.z = getDouble("z", 0.0d);
        this.yaw = getFloat("yaw", 0.0f);
        this.pitch = getFloat("pitch", 0.0f);
        this.name = getString("world", "");
        this.whitelist = getStringList("whitelist", new ArrayList<>());
        this.type = getEnum("type", Type.class, Type.PUBLIC);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        setEnum("type", type);
    }

    public void setLocation(Location location) {
        setDouble("x", location.getX());
        setDouble("y", location.getY());
        setDouble("z", location.getZ());
        setFloat("yaw", location.getYaw());
        setFloat("pitch", location.getPitch());
        setString("world", location.getWorld().getName());
        this.init();
    }

    public Location getLocation() {
        World world = Bukkit.getWorld(name);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public enum Type {
        PUBLIC,
        PRIVATE, // aka personal home
        WHITELISTED
    }
}
