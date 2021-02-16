package me.marcusslover.sloversurvivalreborn.command;

import me.marcusslover.sloversurvivalreborn.code.command.Command;
import me.marcusslover.sloversurvivalreborn.code.command.PlayerCommand;
import me.marcusslover.sloversurvivalreborn.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Command(name = "spawn")
public class SpawnCommand implements PlayerCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        World world = Bukkit.getWorlds().get(0);
        Location location = new Location(world, 0.5, 100, 0.5, 0f, 0f);
        player.teleport(location);
        player.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 2.0f);

        API.sendSuccess(player, "You were teleported to spawn!");
    }
}
