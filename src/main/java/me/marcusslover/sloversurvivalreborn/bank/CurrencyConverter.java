package me.marcusslover.sloversurvivalreborn.bank;

import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.tuple.Pair;

public class CurrencyConverter {
    /**
     * The conversion rate of diamonds to doubloons.
     * If the conversion rate is 2, 1 diamond is 2 doubloons.
     * If the conversion rate is .2, 1 doubloon is 5 diamonds.
     */
    private static volatile double conversionRate = 1;

    public static double getConversionRateValue() {
        return conversionRate;
    }

    /**
     * Gets a conversion rate.
     *
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
