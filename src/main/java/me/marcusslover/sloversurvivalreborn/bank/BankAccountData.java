package me.marcusslover.sloversurvivalreborn.bank;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.bank.accounts.BankAccount;
import me.marcusslover.sloversurvivalreborn.bank.accounts.JointBankAccount;
import me.marcusslover.sloversurvivalreborn.bank.accounts.PlayerBankAccount;
import me.marcusslover.sloversurvivalreborn.code.data.Data;
import me.marcusslover.sloversurvivalreborn.code.data.IFileData;
import me.marcusslover.sloversurvivalreborn.utils.DataUtil;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@Data(path = "bank", type = "json")
public class BankAccountData implements IFileData<BankAccount<?>> {
    public static BankAccountData instance = new BankAccountData();

    private JsonObject bankData;
    private HashMap<UUID, BankAccount<?>> uncached;

    public BankAccountData() {
    }

    @Override
    public void read(String key) {
        UUID id = UUID.fromString(key);
        if (!Bank.accounts.containsKey(id)) {
            File file = getFile(key);
            try {
                JsonObject obj = DataUtil.readJsonElement(file).getAsJsonObject();
                BankAccount<?> account = null;
                boolean cache = true;
                switch (obj.get("type").getAsString().toLowerCase()) {
                    case "player":
                        account = new PlayerBankAccount(id);
                        if (Bukkit.getPlayer(id) == null) {
                            cache = false;
                        }
                        break;
                    case "joint":
                        account = new JointBankAccount(id);
                        break;
                    case "system":
                        Bank.account = new Bank.SystemBankAccount();
                        account = Bank.account;
                }
                if (account != null)
                    account.load(obj);
                if (cache) Bank.accounts.put(id, account);
                else uncached.put(id, account);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void save(String key) {
        BankAccount<?> account = getAccount(key);
        JsonObject obj = account.toJson();
        String type = "unknown";

        if (account instanceof PlayerBankAccount) type = "player";
        if (account instanceof JointBankAccount) type = "joint";
        if (account instanceof Bank.SystemBankAccount) type = "system";

        obj.addProperty("type", type);
        obj.addProperty("id", account.getAccountId().toString());
        try {
            DataUtil.writeJsonElement(obj, getFile(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    JsonObject readBank() {
        if (bankData == null) {
            File file = getFile("bank");
            try {
                bankData = DataUtil.readJsonElement(file).getAsJsonObject();
            } catch (FileNotFoundException e) {
                bankData = new JsonObject();
                saveBank(); // i know, clever right?
            }
        }
        return bankData;
    }

    public void saveBank() {
        bankData.addProperty("rate", CurrencyConverter.getConversionRateValue());
        bankData.addProperty("diamondCount", Bank.getDiamondCount());
        bankData.add("tax", Taxer.getJson());
        bankData.addProperty("timer", Bank.getBankTime());
        try {
            DataUtil.writeJsonElement(bankData, getFile("bank"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BankAccount<?> getAccount(String key) {
        BankAccount<?> account = Bank.accounts.get(UUID.fromString(key));
        if (Bank.isOffline(account)) Bank.accounts.remove(UUID.fromString(key));
        return account;
    }
    public boolean exists(String key) {
        return getFile(key).exists();
    }

    public void setAccount(String key, BankAccount<?> account) {
        Bank.accounts.put(UUID.fromString(key), account);
    }

    @Override
    public void onDisable() {
        Bank.save();
    }
}
