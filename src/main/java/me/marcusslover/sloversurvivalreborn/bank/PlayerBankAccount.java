package me.marcusslover.sloversurvivalreborn.bank;

import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerBankAccount extends BankAccount<PlayerBankAccount> {
    private UUID uuid;

    public PlayerBankAccount(UUID id) {
        super(id);
        this.uuid = id;
    }

    @Override
    public UUID getAccountId() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    @Override
    public void load(JsonObject object) {
        this.balance = object.get("balance").getAsBigDecimal();
        this.uuid = UUID.fromString(object.get("id").getAsString());
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("balance", this.balance);
        return obj;
    }
}
