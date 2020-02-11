package com.ruoyi.framework.util;

public class HexString {
    HexString() {
    }

    public static byte[] toBytes(String hexString) {
        byte[] bs = new byte[hexString.length() / 2];

        for(int i = 0; i < bs.length; ++i) {
            bs[i] = (byte)Integer.parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
        }

        return bs;
    }

    public static String toHexString(byte[] bs) {
        StringBuilder retString = new StringBuilder(64);

        for(int i = 0; i < bs.length; ++i) {
            retString.append(Integer.toHexString(256 + (bs[i] & 255)).substring(1).toUpperCase());
        }

        return retString.toString();
    }

    public static void main(String[] args) {
        System.out.println(toHexString(toBytes("98430FEB003E021E")));
    }
}
