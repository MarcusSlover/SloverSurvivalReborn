package me.marcusslover.sloversurvivalreborn.utils;

@FunctionalInterface
interface IInterpolator {
    double[] interpolate(double from, double to, int max);
}
