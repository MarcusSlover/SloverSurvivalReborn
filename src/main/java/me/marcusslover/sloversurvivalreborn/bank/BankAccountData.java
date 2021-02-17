package me.marcusslover.sloversurvivalreborn.bank;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.code.data.Data;
import me.marcusslover.sloversurvivalreborn.code.data.DataUtil;
import me.marcusslover.sloversurvivalreborn.code.data.IFileData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Data(path = "bank", type = "json")
public class BankAccountData implements IFileData<BankAccount<?>> {

    private JsonObject bankData;

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
                switch (obj.get("type").getAsString().toLowerCase()) {
                    case "player":
                        account = new PlayerBankAccount(id);
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
                Bank.accounts.put(id, account);
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
    }

    public BankAccount<?> getAccount(String key) {
        return Bank.accounts.get(UUID.fromString(key));
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
