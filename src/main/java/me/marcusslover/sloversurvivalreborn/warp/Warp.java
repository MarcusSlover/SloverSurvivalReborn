package me.marcusslover.sloversurvivalreborn.warp;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.utils.JsonModel;

public class Warp extends JsonModel {

    private double x;

    public Warp(JsonObject jsonObject) {
        super(jsonObject);
        this.init();
    }

    private void init() {
        this.x = getDouble("x", 0.0d);
        this.y = getDouble("y", 0.0d);
        this.z = getDouble("z", 0.0d);
        this.pitch = getFl("pitch", 0.0d);
    }

}
