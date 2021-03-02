package me.marcusslover.sloversurvivalreborn.bank;

import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.SloverSurvivalReborn;
import me.marcusslover.sloversurvivalreborn.bank.accounts.BankAccount;
import me.marcusslover.sloversurvivalreborn.bank.accounts.JointBankAccount;
import me.marcusslover.sloversurvivalreborn.bank.accounts.PlayerBankAccount;
import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Bank implements ICodeInitializer {
    public static final int MAX_PRECISION = 16;
    private static volatile long diamondCount;
    static SystemBankAccount account = new SystemBankAccount();

    static volatile Map<UUID, BankAccount<?>> accounts = new HashMap<>();
    private static int updateRunnableTask;

    static {
        accounts.put(account.getAccountId(), account);
    }

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
        BankAccountData.instance.setAccount(player.getUniqueId().toString(), account);
        BankAccountData.instance.save(account.getAccountId().toString());
        return account;
    }

    public static BankAccount<?> loadBankAccount(UUID uuid) {
        if (!accounts.containsKey(uuid)) {
            BankAccountData.instance.read(uuid.toString());
        }
        return BankAccountData.instance.getAccount(uuid.toString());
    }

    public static boolean accountExists(UUID uuid) {
        return BankAccountData.instance.exists(uuid.toString());
    }

    public static List<BankAccount<?>> getAccounts() {
        return new ArrayList<>(accounts.values());
    }

    /**
     * Gets all player accounts, unloaded or loaded.
     * WARNING: This is a very resource intensive operation.
     * @return
     */
    public static List<BankAccount<?>> getAllPlayerAccounts() {
        File[] files = BankAccountData.instance.getDataFolder()
                .listFiles((file) ->
                                file.isFile() &&
                                !file.getName().equals(account.getAccountId().toString()) &&
                                !file.getName().equals("bank.json")
                );
        List<String> ids = Arrays.stream(files)
                .map((file) -> file.getName().substring(0, file.getName().lastIndexOf(".json")))
                .collect(Collectors.toList());
        List<BankAccount<?>> accounts = new ArrayList<>();
        for (String id : ids) {
            accounts.add(loadBankAccount(UUID.fromString(id)));
        }
        return accounts;
    }

    public static void unload(UUID id) {
        BankAccountData.instance.save(id.toString());
        accounts.remove(id);
    }

    public static boolean isOffline(BankAccount<?> account) {
        if (account instanceof PlayerBankAccount) {
            PlayerBankAccount pa = (PlayerBankAccount) account;
            if (pa.getPlayer() == null)
                return true;
        }
        if (account instanceof JointBankAccount) {
            JointBankAccount ja = (JointBankAccount) account;
            if (ja.getParties().stream().allMatch(id -> Bukkit.getPlayer(id) == null)) {
                return true;
            }
        }
        return false;
    }

    static BankUpdateRunnable bankUpdateRunnable;

    public static long getBankTime() {
        return bankUpdateRunnable.getTimer();
    }

    @Override
    public void initialize() {
        JsonObject bankData = BankAccountData.instance.readBank();
        CurrencyConverter.conversionRate = bankData.get("rate").getAsDouble();
        Taxer.load(bankData.get("tax").getAsJsonObject());
        diamondCount = bankData.get("diamondCount").getAsLong();

        BukkitScheduler scheduler = Bukkit.getScheduler();
        bankUpdateRunnable = new BankUpdateRunnable(bankData.get("timer").getAsLong());
        updateRunnableTask = scheduler.scheduleSyncRepeatingTask(SloverSurvivalReborn.getInstance(), bankUpdateRunnable, 0, 20);
    }

    public static void save() {
        for (UUID uuid : accounts.keySet()) {
            BankAccountData.instance.save(uuid.toString());
        }
        BankAccountData.instance.saveBank();
    }


    public static class SystemBankAccount extends BankAccount<SystemBankAccount> {

        public static final String TYPE = "system";

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

        @Override
        public String getType() {
            return TYPE;
        }

        @Override
        public boolean canAfford(BigDecimal cost) {
            return true;
        }

        @Override
        public void save() {
            super.save();
            Bank.account = this;
        }
    }

    public static BigDecimal getAveragePlayerBalance() {
        BigDecimal totalBalance = new BigDecimal("0");
        for (BankAccount<?> account : Bank.getAccounts()) {
            totalBalance = totalBalance.add(account.getBalance());
            if (account.getAccountId() == getSystemAccount().getAccountId())
                totalBalance = totalBalance.subtract(account.getBalance());
        }
        return totalBalance.divide(BigDecimal.valueOf(Bank.accounts.size()), Bank.MAX_PRECISION, RoundingMode.HALF_EVEN);
    }

    private static class BankUpdateRunnable implements Runnable {
        public static final long CONVERSION_UPDATE_INTERVAL = 600;
        public static final long TAX_INTERVAL = 1800;
        public static final long PLAYTIME_INTERVAL = 5;

        private static long timer = 0;

        public BankUpdateRunnable(long timer) {
            BankUpdateRunnable.timer = timer;
        }

        public long getTimer() {
            return timer;
        }

        @Override
        public void run() {
            timer++;
            if (timer % PLAYTIME_INTERVAL == 0) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    Taxer.addPlayTime(player, PLAYTIME_INTERVAL);
                }
            }
            if (timer % CONVERSION_UPDATE_INTERVAL == 0) {
                CurrencyConverter.update();
            }
            if (timer % TAX_INTERVAL == 0) {
                Taxer.taxPlayers(TAX_INTERVAL);
            }
        }

    }
}
