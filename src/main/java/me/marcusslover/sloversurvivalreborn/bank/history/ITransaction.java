package me.marcusslover.sloversurvivalreborn.bank.history;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.code.data.IJsonable;

public interface ITransaction extends IJsonable<JsonObject> {
    long getTimestamp();
    void setTransactionId(String str);
    String getTransactionId();

    @Override
    String toString();

    String getType();
}
