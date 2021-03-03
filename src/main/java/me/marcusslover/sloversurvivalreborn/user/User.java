package me.marcusslover.sloversurvivalreborn.user;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.code.CodeInitializer;
import me.marcusslover.sloversurvivalreborn.rank.Rank;
import me.marcusslover.sloversurvivalreborn.rank.RankHandler;
import me.marcusslover.sloversurvivalreborn.utils.JsonModel;

public class User extends JsonModel {

    private Rank rank;

    public User(JsonObject jsonObject) {
        super(jsonObject);
        this.init();
    }

    public void init() {
        String rankId = this.getString("rank", "rookie");
        RankHandler rankHandler = CodeInitializer.find(RankHandler.class);
        Rank rank = rankHandler.find(rankId);
        this.rank = rank;

    }

    public Rank getRank() {
        return rank;
    }
}
