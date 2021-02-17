package me.marcusslover.sloversurvivalreborn.bank;

import com.google.gson.JsonObject;

import java.util.Map;
import java.util.UUID;

public class Bank {
    public static final int MAX_BALANCE_PRECISION = 16;

    private static volatile long diamondCount;
    private static SystemBankAccount account;
    private static Map<UUID, BankAccount<?>> accounts;

    public static long getDiamondCount() {
        return diamondCount;
    }

    static void setDiamondCount(long diamondCount) {
        Bank.diamondCount = diamondCount;
    }

    public static class SystemBankAccount extends BankAccount<SystemBankAccount> {

        @Override
        public UUID getAccountId() {
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
        }

        @Override
        public void load(JsonObject object) {
            this.balance = object.get("balance").getAsBigDecimal();
        }

        @Override
        public JsonObject toJson() {
            JsonObject obj = new JsonObject();
            obj.addProperty("balance", this.balance);
            return obj;
        }
    }
}
