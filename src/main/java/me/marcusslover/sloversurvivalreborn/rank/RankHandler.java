package me.marcusslover.sloversurvivalreborn.rank;

import me.marcusslover.sloversurvivalreborn.code.Init;
import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.IHandler;

import java.util.List;

public class RankHandler implements ICodeInitializer, IHandler<Rank> {

    @Init
    private List<Rank> rankList;

    @Override
    public void initialize() {
        add(new StaffRank("leader", "%gradient(#FF0000, #6D4B9A, [Leader])", "#FF0000"));
        add(new StaffRank("staff", "%gradient(#6D4B9A, #00AE92, [Staff])", "#6D4B9A"));
        add(new SpecialRank("amethyst", "%gradient(#8c00ab, #ed3bd2, (Amethyst))", "#8c00ab"));
        add(new SpecialRank("ametrine", "%gradient(#ffadfa, #c75eeb, (Ametrine))", "#ffadfa"));
        add(new SpecialRank("aquamarine", "%gradient(#00ffea, #b3fff9, (Aquamarine))", "#00ffea"));
        add(new SpecialRank("emerald", "%gradient(#00f269, #54ffba, (Emerald))", "#00f269"));
        add(new SpecialRank("garnet", "%gradient(#ff7400, #ffa05f, (Garnet))", "#ff7400"));
        add(new SpecialRank("jade", "%gradient(#64b01c, #a2f453, (Jade))", "#64b01c"));
        add(new SpecialRank("rookie", "%gradient(#8bb3c3, #516d7e, (Rookie))", "#8bb3c3"));
    }

    @Override
    public void add(Rank object) {
        rankList.add(object);
    }

    @Override
    public List<Rank> getRegistered() {
        return rankList;
    }

    public Rank find(String name) {
        for (Rank rank : rankList) {
            if (rank.getName().equalsIgnoreCase(name)) {
                return rank;
            }
        }
        return null;
    }
}
