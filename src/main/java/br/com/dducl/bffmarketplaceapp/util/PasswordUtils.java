package br.com.dducl.bffmarketplaceapp.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    private static final String ALGORITHM = "SHA-256";

    public static String encrypt(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            byte[] hashBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));

            return new String(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm");
        }
    }
}
