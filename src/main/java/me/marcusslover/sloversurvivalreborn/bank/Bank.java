package me.marcusslover.sloversurvivalreborn.bank;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.google.common.math.BigIntegerMath;
import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.code.ICodeInitializer;
import me.marcusslover.sloversurvivalreborn.code.Init;
import me.marcusslover.sloversurvivalreborn.code.data.FileDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Bank implements ICodeInitializer {
    public static final int MAX_PRECISION = 16;
    private static volatile long diamondCount;
    static SystemBankAccount account = new SystemBankAccount();

    static Map<UUID, BankAccount<?>> accounts = new HashMap<>();

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
        return account;
    }

    public static BankAccount<?> loadBankAccount(UUID uuid) {
        if (!accounts.containsKey(uuid)) {
            BankAccountData.instance.read(uuid.toString());
        }
        return accounts.get(uuid);
    }

    public static boolean accountExists(UUID uuid) {
        return BankAccountData.instance.exists(uuid.toString());
    }

    public static void unload(UUID id) {
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

    @Override
    public void initialize() {
        JsonObject bankData = BankAccountData.instance.readBank();
        CurrencyConverter.conversionRate = bankData.get("rate").getAsDouble();
        diamondCount = bankData.get("diamondCount").getAsLong();
    }

    public static void save() {
        for (UUID uuid : accounts.keySet()) {
            BankAccountData.instance.save(uuid.toString());
        }
        BankAccountData.instance.saveBank();
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

    public static BigDecimal getAverageBalance() {
        BigDecimal totalBalance = new BigDecimal("0");
        for (BankAccount<?> account : Bank.accounts.values()) {
            totalBalance = totalBalance.add(account.getBalance());
        }
        return totalBalance.divide(BigDecimal.valueOf(Bank.accounts.size()), Bank.MAX_PRECISION, RoundingMode.HALF_EVEN);
    }

    private static final BigDecimal BEGIN_TAX = new BigDecimal(".25");
    private static final BigDecimal BEGIN_GREAT_TAX = new BigDecimal("1.75");
    private static MathContext mctx = new MathContext(MAX_PRECISION + 1);

    public static double getTaxRate(BigDecimal balance) {
        BigDecimal avgBalance = getAverageBalance();

        BigDecimal taxBegin = avgBalance.multiply(BEGIN_TAX);
        if (balance.compareTo(taxBegin) > 0) {
            BigDecimal greatTaxBegin = avgBalance.multiply(BEGIN_GREAT_TAX);
            if (balance.compareTo(greatTaxBegin) > 0) {
                BigDecimal ratio = balance.divide(greatTaxBegin);
                return Math.max(getTaxRate(avgBalance), Math.min(.325, BigDecimalMath.log10(ratio, mctx).divide(BigDecimal.valueOf(2), mctx).doubleValue()));
            }
            BigDecimal ratio = balance.divide(taxBegin, mctx);
            return Math.max(.05, BigDecimalMath.log10(ratio.add(BigDecimal.valueOf(1), mctx), mctx).divide(BigDecimal.valueOf(1.75), mctx).doubleValue());
        }

        return 0;
    }
}
