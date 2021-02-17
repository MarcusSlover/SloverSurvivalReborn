package me.marcusslover.sloversurvivalreborn.utils;

@FunctionalInterface
public interface IInterpolator {
    double[] interpolate(double from, double to, int max);
}
