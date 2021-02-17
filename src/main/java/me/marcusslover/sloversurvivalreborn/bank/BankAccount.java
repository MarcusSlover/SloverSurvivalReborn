package me.marcusslover.sloversurvivalreborn.bank;

import com.google.gson.JsonObject;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BankAccount<TThis extends BankAccount<TThis>> {
    protected BigDecimal balance;

    public BigDecimal getBalance() {
        return balance;
    }
    public void transferBalance(BigDecimal amount, BankAccount<?> other) {
        this.balance = this.balance.subtract(amount);
        other.addBalance(amount);
    }
    public void removeBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
    public void addBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public abstract UUID getAccountId();
    public abstract void load(JsonObject object);
    public abstract JsonObject toJson();
}
