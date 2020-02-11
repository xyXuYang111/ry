package com.ruoyi.framework.util;

import org.apache.commons.codec.binary.Base64;

public class BASE64 {
    private static final String DEFAULT_CHARSET = "UTF-8";

    BASE64() {
    }

    public static String toBASE64String(String s) throws Exception {
        return s == null ? null : Base64.encodeBase64String(s.getBytes("UTF-8"));
    }

    public static String toBASE64String(byte[] bs) {

        return bs == null ? null : Base64.encodeBase64String(bs);
    }

    public static String getStringFromBASE64String(String s) throws Exception {
        return s == null ? null : new String(getBytesFromBASE64String(s), "UTF-8");
    }

    public static byte[] getBytesFromBASE64String(String s) throws Exception {
        byte[] b =  Base64.decodeBase64(s);
        return b;
    }
}