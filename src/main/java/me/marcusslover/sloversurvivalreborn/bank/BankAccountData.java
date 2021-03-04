package me.marcusslover.sloversurvivalreborn.bank;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.bank.accounts.BankAccount;
import me.marcusslover.sloversurvivalreborn.bank.accounts.JointBankAccount;
import me.marcusslover.sloversurvivalreborn.bank.accounts.PlayerBankAccount;
import me.marcusslover.sloversurvivalreborn.code.data.Data;
import me.marcusslover.sloversurvivalreborn.code.data.IFileData;
import me.marcusslover.sloversurvivalreborn.utils.DataUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Data(path = "bank", type = "json")
public class BankAccountData implements IFileData<BankAccount<?>> {
    public static BankAccountData instance = new BankAccountData();

    private JsonObject bankData;

    public BankAccountData() {
    }

    @Override
    public void read(String key) {
        UUID id = UUID.fromString(key);
        if (!Bank.accounts.containsKey(id)) {
            File file = getFile(key, false);
            try {
                JsonObject obj = DataUtil.readJsonElement(file).getAsJsonObject();
                BankAccount<?> account = null;
                switch (obj.get("type").getAsString().toLowerCase()) {
                    case PlayerBankAccount.TYPE:
                        account = new PlayerBankAccount(id);
                        break;
                    case JointBankAccount.TYPE:
                        account = new JointBankAccount(id);
                        break;
                    case Bank.SystemBankAccount.TYPE:
                        Bank.account = new Bank.SystemBankAccount();
                        account = Bank.account;
                        break;
                    default:
                        System.out.println("Invalid account type");
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
        obj.addProperty("type", account.getType());
        obj.addProperty("id", account.getAccountId().toString());
        try {
            DataUtil.writeJsonElement(obj, getFile(key, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    JsonObject readBank() {
        if (bankData == null) {
            File file = getFile("bank", false);
            bankData = DataUtil.readJsonElement(file, new JsonObject()).getAsJsonObject();
        }
        if (bankData.size() <= 0) {
            this.save("bank");
        }

        return bankData;
    }

    public void saveBank() {
        bankData.addProperty("rate", CurrencyConverter.getConversionRateValue());
        bankData.addProperty("diamondCount", Bank.getDiamondCount());
        bankData.add("tax", Taxer.getJson());
        bankData.addProperty("timer", Bank.getBankTime());
        try {
            DataUtil.writeJsonElement(bankData, getFile("bank", true));
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
        return getFile(key, false).exists();
    }

    public void setAccount(String key, BankAccount<?> account) {
        Bank.accounts.put(UUID.fromString(key), account);
    }

    @Override
    public void onDisable() {
        Bank.save();
    }
}
