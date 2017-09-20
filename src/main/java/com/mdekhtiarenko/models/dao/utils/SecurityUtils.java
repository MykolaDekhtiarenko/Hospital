package com.mdekhtiarenko.models.dao.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mykola.dekhtiarenko on 24.08.17.
 */
public class SecurityUtils {
    private static final String HASH_ALGORITHM = "MD5";

    public static String decode(final String inputString) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(inputString.getBytes());
        byte[] digest = md.digest();
        return convertByteToHex(digest);
    }

    private static String convertByteToHex(byte[] byteData) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
