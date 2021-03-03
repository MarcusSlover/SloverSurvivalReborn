package me.marcusslover.sloversurvivalreborn.utils;

import me.marcusslover.sloversurvivalreborn.code.CodeInitializer;
import me.marcusslover.sloversurvivalreborn.rank.Rank;
import me.marcusslover.sloversurvivalreborn.rank.RankHandler;
import me.marcusslover.sloversurvivalreborn.user.User;
import me.marcusslover.sloversurvivalreborn.user.UserFileData;
import org.bukkit.entity.Player;

public class RankUtil {

    public static boolean hasRank(Player player, String rank) {
        Rank rank1 = findRank(rank);
        if (rank1 != null) {
            User user = UserFileData.getInstance().getMap().get(player.getUniqueId().toString());
            if (user != null) {
                Rank rank2 = user.getRank();
                return rank2.getPriority() >= rank1.getPriority();
            }
        }
        return false;
    }

    public static Rank findRank(String rank) {
        RankHandler rankHandler = CodeInitializer.find(RankHandler.class);
        return rankHandler.find(rank);
    }

    public static void missingMessage(Player player, String rank) {
        Rank rank1 = findRank(rank);
        ChatUtil.error(player, "You have to be "+rank1.getPrefix()+"%hex(#CBCBCB) or higher!");
    }
}
