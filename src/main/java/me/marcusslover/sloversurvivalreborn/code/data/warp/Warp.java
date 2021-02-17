package me.marcusslover.sloversurvivalreborn.code.data.warp;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.code.CodeInitializer;
import me.marcusslover.sloversurvivalreborn.rank.Rank;
import me.marcusslover.sloversurvivalreborn.rank.RankHandler;
import me.marcusslover.sloversurvivalreborn.utils.JsonWrapper;

public class Warp extends JsonWrapper {

    public Warp(JsonObject jsonObject) {
        super(jsonObject);
        this.refresh();
    }

    public void refresh() {


    }
}
