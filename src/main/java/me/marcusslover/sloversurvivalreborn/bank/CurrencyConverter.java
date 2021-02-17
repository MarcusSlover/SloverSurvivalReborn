package me.marcusslover.sloversurvivalreborn.bank;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {
    /**
     * The conversion rate of diamonds to doubloons.
     * If the conversion rate is 2, 1 diamond is 2 doubloons.
     * If the conversion rate is .2, 1 doubloon is 5 diamonds.
     */
    static volatile double conversionRate = 100;

    public static double getConversionRateValue() {
        return conversionRate;
    }

    public static void update() {
        BigDecimal totalBalance = new BigDecimal("0");
        for (BankAccount<?> account : Bank.accounts.values()) {
            totalBalance = totalBalance.add(account.getBalance());
        }
        totalBalance = totalBalance.divide(BigDecimal.valueOf(Bank.accounts.size()), Bank.MAX_PRECISION, RoundingMode.HALF_EVEN);
        conversionRate = totalBalance.divide(BigDecimal.valueOf(Bank.getDiamondCount()), Bank.MAX_PRECISION, RoundingMode.HALF_EVEN).doubleValue();
    }

    /**
     * Gets a conversion rate.
     * @return The conversion rate. The diamonds will be rounded.
     */
    public static ConversionRate getConversionRate() {
        if (getConversionRateValue() < 1) {
            return new ConversionRate((long) (1 / getConversionRateValue()), 1);
        } else {
            return new ConversionRate(1, 1 * getConversionRateValue());
        }
    }

    public static class ConversionRate {
        private long diamonds;
        private double doubloons;

        ConversionRate(long diamonds, double doubloons) {
            this.diamonds = diamonds;
            this.doubloons = doubloons;
        }

        public double convertToDoubloons(long diamonds) {
            update();
            return diamonds * doubloons;
        }

        public Pair<Long, Double> convertToDiamonds(double doubloons) {
            update();
            double remainder = doubloons % this.doubloons;
            return Pair.of(diamonds * (long) (doubloons - remainder), remainder);
        }

        public void update() {
            ConversionRate rate = CurrencyConverter.getConversionRate();
            this.diamonds = rate.diamonds;
            this.doubloons = rate.doubloons;
        }
    }
}
