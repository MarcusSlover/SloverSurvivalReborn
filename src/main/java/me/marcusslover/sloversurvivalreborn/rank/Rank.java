package me.marcusslover.sloversurvivalreborn.rank;

public class Rank {

    private static int rankPriorities = 10;

    private final String name;
    private final String prefix;
    private final String color;
    private final int priority;

    public Rank(String name, String prefix, String color) {

        this.name = name;
        this.prefix = prefix;
        this.color = color;
        this.priority = rankPriorities;
        rankPriorities--;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getColor() {
        return color;
    }

    public int getPriority() {
        return priority;
    }
}
