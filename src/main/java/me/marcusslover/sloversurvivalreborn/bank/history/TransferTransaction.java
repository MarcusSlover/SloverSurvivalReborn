package me.marcusslover.sloversurvivalreborn.bank.history;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.bank.accounts.BankAccount;

import java.math.BigDecimal;
import java.util.UUID;

public class TransferTransaction implements ITransaction {
    public static final String TYPE = "transfer";
    private String transactionId = null;
    protected UUID from;
    protected UUID to;
    protected BigDecimal amount;
    protected long timestamp;

    public TransferTransaction(BankAccount<?> from, BankAccount<?> to, BigDecimal amount) {
        this.from = from.getAccountId();
        this.to = to.getAccountId();
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public long getTimestamp() {
        return this.timestamp;
    }

    @Override
    public void setTransactionId(String str) {
        if (transactionId == null) this.transactionId = str;
    }

    @Override
    public String getTransactionId() {
        return this.transactionId;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("from", from.toString());
        obj.addProperty("to", to.toString());
        obj.addProperty("amount", amount.toString());
        return obj;
    }

    @Override
    public void load(JsonObject obj) {
        this.from = UUID.fromString(obj.get("from").getAsString());
        this.to = UUID.fromString(obj.get("to").getAsString());
        this.amount = obj.get("amount").getAsBigDecimal();
        this.timestamp = obj.get("timestamp").getAsLong();
    }

    public UUID getFrom() {
        return from;
    }

    public UUID getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
