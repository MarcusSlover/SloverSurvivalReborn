package me.marcusslover.sloversurvivalreborn.warp;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.utils.JsonModel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Warp extends JsonModel {

    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private String worldName;
    private List<String> whitelist;
    private Type type;
    private UUID owner;
    private UUID warpId;
    private String warpName;
    private Material warpIcon;

    public Warp(JsonObject jsonObject) {
        super(jsonObject);
        this.init();
    }

    private void init() {
        this.warpId = getUUID("id", UUID.randomUUID());
        this.warpName = getString("name", "Warp");
        this.warpIcon = getMaterial("icon", Material.DIRT);
        this.owner = getUUID("owner", null);
        this.x = getDouble("x", 0.0d);
        this.y = getDouble("y", 0.0d);
        this.z = getDouble("z", 0.0d);
        this.yaw = getFloat("yaw", 0.0f);
        this.pitch = getFloat("pitch", 0.0f);
        this.worldName = getString("world", "");
        this.whitelist = getStringList("whitelist", new ArrayList<>());
        this.type = getEnum("type", Type.class, Type.PRIVATE);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
        setEnum("type", type);
    }

    public void setLocation(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.worldName = location.getWorld().getName();
        setDouble("x", location.getX());
        setDouble("y", location.getY());
        setDouble("z", location.getZ());
        setFloat("yaw", location.getYaw());
        setFloat("pitch", location.getPitch());
        setString("world", location.getWorld().getName());
    }

    public Location getLocation() {
        World world = Bukkit.getWorld(worldName);
        return new Location(world, x, y, z, yaw, pitch);
    }

    public boolean isWhitelisted(Player player) {
        return this.whitelist.contains(player.getUniqueId().toString());
    }

    public boolean whitelist(Player player) {
        UUID uniqueId = player.getUniqueId();
        String string = uniqueId.toString();
        boolean added = false;
        if (this.whitelist.contains(string)) {
            this.whitelist.remove(string);
        } else {
            this.whitelist.add(string);
            added = true;
        }
        this.setWhitelist(this.whitelist);
        return added;
    }

    public void setWhitelist(List<String> whitelist) {
        this.whitelist = whitelist;
        setStringList("whitelist", whitelist);
    }

    public List<String> getWhitelist() {
        return whitelist;
    }

    public String getWarpName() {
        return warpName;
    }

    public void setWarpName(String warpName) {
        this.warpName = warpName;
        setString("name", warpName);
    }

    public Material getWarpIcon() {
        return warpIcon;
    }

    public void setWarpIcon(Material warpIcon) {
        this.warpIcon = warpIcon;
        setMaterial("icon", warpIcon);
    }

    public UUID getWarpId() {
        return warpId;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public enum Type {
        PUBLIC,
        PRIVATE, // aka personal home
        WHITELISTED
    }
}
