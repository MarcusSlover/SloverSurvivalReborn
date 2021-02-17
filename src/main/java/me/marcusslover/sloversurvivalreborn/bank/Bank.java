package me.marcusslover.sloversurvivalreborn.bank;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.Init;
import me.marcusslover.sloversurvivalreborn.code.data.FileDataHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bank implements ICodeInitializer {
    public static final int MAX_PRECISION = 16;

    private static volatile long diamondCount;
    static SystemBankAccount account = new SystemBankAccount();
    private static FileDataHandler fdh = new FileDataHandler();
    private static BankAccountData data = new BankAccountData();

    static Map<UUID, BankAccount<?>> accounts = new HashMap<>();

    public static long getDiamondCount() {
        return diamondCount;
    }

    static void setDiamondCount(long diamondCount) {
        Bank.diamondCount = diamondCount;
    }

    public static SystemBankAccount getSystemAccount() {
        return account;
    }

    public static PlayerBankAccount createPlayerAccount(Player player) {
        if (accountExists(player.getUniqueId()))
            return null;
        PlayerBankAccount account = new PlayerBankAccount(player.getUniqueId());
        data.setAccount(player.getUniqueId().toString(), account);
        return account;
    }

    public static BankAccount<?> loadBankAccount(UUID uuid) {
        if (!accounts.containsKey(uuid)) {
            data.read(uuid.toString());
        }
        return accounts.get(uuid);
    }

    public static boolean accountExists(UUID uuid) {
        return data.exists(uuid.toString());
    }

    @Override
    public void initialize() {
        JsonObject bankData = data.readBank();
        CurrencyConverter.conversionRate = bankData.get("rate").getAsDouble();
        diamondCount = bankData.getAsLong();
    }

    public static void save() {
        for (UUID uuid : accounts.keySet()) {
            data.save(uuid.toString());
        }
        data.saveBank();
    }


    public static class SystemBankAccount extends BankAccount<SystemBankAccount> {

        public SystemBankAccount() {
            super(null);
        }

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
