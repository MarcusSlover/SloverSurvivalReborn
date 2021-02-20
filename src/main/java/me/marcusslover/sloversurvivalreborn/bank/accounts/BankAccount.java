package me.marcusslover.sloversurvivalreborn.bank.accounts;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.bank.Bank;
import me.marcusslover.sloversurvivalreborn.bank.BankAccountData;
import me.marcusslover.sloversurvivalreborn.bank.history.TransactionHistory;
import me.marcusslover.sloversurvivalreborn.bank.history.TransferTransaction;
import me.marcusslover.sloversurvivalreborn.code.data.IJsonable;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class BankAccount<TThis extends BankAccount<TThis>> implements IJsonable<JsonObject> {
    protected BigDecimal balance;
    protected TransactionHistory history;

    public BankAccount(UUID id) {
        balance = new BigDecimal("256");
        history = new TransactionHistory();
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void transferBalance(BigDecimal amount, BankAccount<?> other) {
        if (other.getAccountId() == this.getAccountId()) return;
        this.removeBalance(amount);
        other.addBalance(amount);
        history.addTransaction(new TransferTransaction(this, other, amount));
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

    public void load(JsonObject object) {
        this.balance = object.get("balance").getAsBigDecimal();
        this.history.load(object.get("history").getAsJsonArray());
    }
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("balance", this.balance);
        obj.add("history", this.history.toJson());
        obj.addProperty("id", this.getAccountId().toString());
        obj.addProperty("type", this.getType());
        return obj;
    }

    public abstract String getType();


    public void save() {
        BankAccountData.instance.setAccount(getAccountId().toString(), this);
    }

    public boolean canAfford(BigDecimal cost) {
        return balance.compareTo(cost) > -1;
    }

    public TransactionHistory getHistory() {
        return this.history;
    }
}
