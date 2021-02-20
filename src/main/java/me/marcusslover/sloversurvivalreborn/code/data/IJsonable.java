package me.marcusslover.sloversurvivalreborn.code.data;

import com.google.gson.JsonElement;

public interface IJsonable<J extends JsonElement> {
    J toJson();
    void load(J element);
}
