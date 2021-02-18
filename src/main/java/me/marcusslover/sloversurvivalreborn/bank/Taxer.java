package me.marcusslover.sloversurvivalreborn.bank;

import ch.obermuhlner.math.big.BigDecimalMath;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.marcusslover.sloversurvivalreborn.bank.accounts.BankAccount;
import me.marcusslover.sloversurvivalreborn.bank.accounts.PlayerBankAccount;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Taxer {
    private static final BigDecimal BEGIN_TAX = new BigDecimal(".25");
    private static final BigDecimal BEGIN_GREAT_TAX = new BigDecimal("1.75");
    private static MathContext mctx = new MathContext(Bank.MAX_PRECISION + 1);

    private static Map<UUID, Long> playtime = new HashMap<>();

    static void addPlayTime(Player player, long count) {
        playtime.put(player.getUniqueId(), playtime.getOrDefault(player.getUniqueId(), 0L) + count);
    }

    public static double getTaxRate(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) < 0)
            return 0;
        BigDecimal avgBalance = Bank.getAveragePlayerBalance();

        BigDecimal taxBegin = avgBalance.multiply(BEGIN_TAX);
        if (balance.compareTo(taxBegin) > 0) {

            BigDecimal greatTaxBegin = avgBalance.multiply(BEGIN_GREAT_TAX);
            if (balance.compareTo(greatTaxBegin) > 0) {
                BigDecimal ratio = balance.divide(greatTaxBegin, mctx);
                return Math.max(getTaxRate(avgBalance), Math.min(.325, BigDecimalMath.log10(ratio, mctx).divide(BigDecimal.valueOf(2), mctx).doubleValue()));
            }

            BigDecimal ratio = balance.divide(taxBegin, mctx);
            return Math.max(.05, BigDecimalMath.log10(ratio.add(BigDecimal.valueOf(1), mctx), mctx).divide(BigDecimal.valueOf(1.75), mctx).doubleValue());
        }

        return 0;
    }

    @SuppressWarnings("SameParameterValue")
    static void taxPlayers(long timeSinceLastTax) {
        List<BankAccount<?>> accounts = Bank.getAllPlayerAccounts();
        for (BankAccount<?> account : accounts) {
            double taxRate = Taxer.getTaxRate(account.getBalance());
            BigDecimal tax = account.getBalance().multiply(BigDecimal.valueOf(taxRate), mctx);
            if (account instanceof PlayerBankAccount) {
                BigDecimal bigTSLT = BigDecimal.valueOf(timeSinceLastTax);
                BigDecimal playtime = BigDecimal.valueOf(Taxer.playtime.get(account.getAccountId()));
                BigDecimal playtimeMultiplier = playtime.divide(bigTSLT, mctx);
                tax = tax.multiply(playtimeMultiplier);
            }
            account.transferBalance(tax, Bank.getSystemAccount());
        }
    }

    public static void load(JsonObject obj) {
        for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
            playtime.put(UUID.fromString(entry.getKey()), entry.getValue().getAsLong());
        }
    }

    public static JsonObject getJson() {
        JsonObject obj = new JsonObject();
        for (Map.Entry<UUID, Long> entry : playtime.entrySet()) {
            obj.addProperty(entry.getKey().toString(), entry.getValue());
        }
        return obj;
    }
}
