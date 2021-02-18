package me.marcusslover.sloversurvivalreborn.bank.accounts;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.bank.Bank;
import me.marcusslover.sloversurvivalreborn.bank.BankAccountData;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BankAccount<TThis extends BankAccount<TThis>> {
    protected BigDecimal balance;

    public BankAccount(UUID id) {
        balance = new BigDecimal("256");
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void transferBalance(BigDecimal amount, BankAccount<?> other) {
        this.removeBalance(amount);
        other.addBalance(amount);
        this.save();
        other.save();
    }

    public void removeBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
        this.save();
    }

    public void addBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
        this.save();
    }

    public abstract UUID getAccountId();

    public abstract void load(JsonObject object);

    public abstract JsonObject toJson();

    public void save() {
        BankAccountData.instance.setAccount(getAccountId().toString(), this);
    }
}
