package me.marcusslover.sloversurvivalreborn.utils;

public class StringUtil {
    public static String hexadecimal(byte[] source) {
        StringBuilder hexString = new StringBuilder(2 * source.length);
        for (byte b : source) {
            String hex = Integer.toHexString(b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
