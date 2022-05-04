package com.datamelt.artikel.app.web.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashGenerator
{
    private static final String SALT = "A1bC3ut709lLpQ";

    public static String generate(String password)
    {
        return generateHash(SALT + password);
    }

    private static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx)
            {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return hash.toString();
    }
}
