package me.marcusslover.sloversurvivalreborn.bank.history;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.bank.Bank;
import me.marcusslover.sloversurvivalreborn.bank.accounts.BankAccount;

import java.math.BigDecimal;
import java.util.UUID;

public class TaxTransaction implements ITransaction {
    public static final String TYPE = "tax";
    private String transactionId = null;
    protected long timestamp;
    protected UUID account;
    protected BigDecimal amount;

    public TaxTransaction(BankAccount<?> account, BigDecimal amount) {
        this.account = account.getAccountId();
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public void setTransactionId(String str) {
        this.transactionId = str;
    }

    @Override
    public String getTransactionId() {
        return this.transactionId;
    }

    @Override
    public String getType() {
        return "tax";
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("account", account.toString());
        obj.addProperty("amount", amount);
        obj.addProperty("timestamp", timestamp);
        return obj;
    }

    @Override
    public void load(JsonObject obj) {
        this.account = UUID.fromString(obj.get("account").getAsString());
        this.amount = obj.get("amount").getAsBigDecimal();
        this.timestamp = obj.get("timestamp").getAsLong();
    }

    public UUID getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
