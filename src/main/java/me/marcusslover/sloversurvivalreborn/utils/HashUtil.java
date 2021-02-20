package me.marcusslover.sloversurvivalreborn.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    public static byte[] sha256(byte[] source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.digest(source);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[]{};
        }
    }
}
