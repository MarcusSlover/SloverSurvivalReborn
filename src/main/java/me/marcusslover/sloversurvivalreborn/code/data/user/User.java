package me.marcusslover.sloversurvivalreborn.code.data.user;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.code.CodeInitializer;
import me.marcusslover.sloversurvivalreborn.utils.JsonWrapper;
import me.marcusslover.sloversurvivalreborn.rank.Rank;
import me.marcusslover.sloversurvivalreborn.rank.RankHandler;

public class User extends JsonWrapper {

    private Rank rank;

    public User(JsonObject jsonObject) {
        super(jsonObject);
        this.refresh();
    }

    public void refresh() {
        String rankId = this.getString("rank", "rookie");
        RankHandler rankHandler = CodeInitializer.find(RankHandler.class);
        Rank rank = rankHandler.find(rankId);
        this.rank = rank;

    }

    public Rank getRank() {
        return rank;
    }
}
