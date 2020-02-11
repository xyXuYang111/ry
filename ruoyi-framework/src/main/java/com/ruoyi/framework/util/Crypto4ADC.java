package com.ruoyi.framework.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public class Crypto4ADC {
    private static final String DEFAULT_CHARSET = "UTF-8";

    public Crypto4ADC() {
    }

    public static String decode(String key, String cipher_value) throws GeneralSecurityException, UnsupportedEncodingException, Exception {
        String plainValueLevel = new String((new DES4ADC(key)).doDecrypt(BASE64.getBytesFromBASE64String(cipher_value)), "UTF-8");
        String forePart = plainValueLevel.substring(0, 32);
        String backEnd = plainValueLevel.substring(32);
        MessageDigest md = MessageDigest.getInstance("MD5");
        String backEndForePart = HexString.toHexString(md.digest(backEnd.getBytes("UTF-8"))).toLowerCase();
        return !forePart.equals(backEndForePart) ? null : backEnd;
    }

    public static String encode(String key, String plain_value) throws GeneralSecurityException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String forePart = HexString.toHexString(md.digest(plain_value.getBytes("UTF-8"))).toLowerCase();
        String plainText = forePart + plain_value;
        return BASE64.toBASE64String((new DES4ADC(key)).doEncrypt(plainText.getBytes("UTF-8")));
    }

    public static void main(String[] args) throws Exception {
        String type = "bzyal+ywZ4HQCmcGG96NnrTfT1Pkj5kWo4x+QtMLdtD6004O8sPcKw==";
        System.out.println(decode("c2332e42996e450ca1be7217174b7abb", type));

        String driverClass = "gz+QKXl6e7PM6EDb/cUhT8NdW6neimPqhSOHFsOmOvcafMpp4iEE5V65/fEYir1fP69H7IgNX3hoSqF7oIyxoQ==";
        System.out.println(decode("c2332e42996e450ca1be7217174b7abb", driverClass));

        String jdbcUrl = "xL8Dscp0q5T9Rk7Eq/SPTyGihls1Qcl3qZfKdJf0U+T6WOE0E/T2jMsMZKEceXJtFw17piSzgJx9lTBJCcGYEpWcojdsP5AC";
        System.out.println(decode("c2332e42996e450ca1be7217174b7abb", jdbcUrl));

        String user = "+rr7V5KzbRpqqRxpmYAoNbpvDgkNBZd8Uuroo91944DjxQGLs16Qhg==";
        System.out.println(decode("c2332e42996e450ca1be7217174b7abb", user));

        String password = "MFDU7tZ9V8fXsxkZnzA8UgPbO9i/qey8KzBlXaPXkoyxyLRswDWCacGH3QZtxnERcS3eIPJ24AM=";
        System.out.println(decode("c2332e42996e450ca1be7217174b7abb", password));
    }
}