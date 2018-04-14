package com.bingco.Util;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static String md5(String context) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bys = md.digest(context.getBytes(StandardCharsets.UTF_8));

            String hex;
            StringBuilder sb = new StringBuilder();
            for (int x = 0, len = bys.length; x < len; x++) {
                sb = (hex = Integer.toHexString(bys[x] & 0xf)).length() >= 2 ? sb.append(hex) : sb.append("0").append(hex);
            }
            return sb.toString();
        }

        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
