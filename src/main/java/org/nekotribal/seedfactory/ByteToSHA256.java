package org.nekotribal.seedfactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ByteToSHA256 {

    private ByteToSHA256() {
    }

    public static byte[] byteTableToSHA256(byte[] originalBytes) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest.digest(originalBytes);
    }

    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
