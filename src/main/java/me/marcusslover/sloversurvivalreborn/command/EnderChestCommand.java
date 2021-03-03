package me.marcusslover.sloversurvivalreborn.command;

import me.marcusslover.sloversurvivalreborn.code.command.Command;
import me.marcusslover.sloversurvivalreborn.code.command.PlayerCommand;
import me.marcusslover.sloversurvivalreborn.utils.RankUtil;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Command(name = "enderchest", aliases = {"ec"})
public class EnderChestCommand implements PlayerCommand {

    @Override
    public void onCommand(Player player, String[] args) {
        if (RankUtil.hasRank(player, "emerald")) {
            Inventory enderChest = player.getEnderChest();
            player.openInventory(enderChest);
        } else {
            RankUtil.missingMessage(player, "emerald");
        }
    }
}
