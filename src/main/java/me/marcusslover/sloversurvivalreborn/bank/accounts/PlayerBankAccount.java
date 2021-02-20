package me.marcusslover.sloversurvivalreborn.bank.accounts;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.bank.Bank;
import me.marcusslover.sloversurvivalreborn.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.UUID;

public class PlayerBankAccount extends BankAccount<PlayerBankAccount> {
    public static final String TYPE = "player";
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

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void transferBalance(BigDecimal amount, BankAccount<?> other) {
        super.transferBalance(amount, other);
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            if (getPlayer() != null) {
                API.sendMessage(getPlayer(), "%hex(f02222)You have outstanding balance!\nYour current balance: " + balance.toString());
            }
        }
    }
}
