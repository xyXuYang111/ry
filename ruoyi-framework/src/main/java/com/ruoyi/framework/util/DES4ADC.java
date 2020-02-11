package com.ruoyi.framework.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;

public class DES4ADC {
    private byte[] desKey;
    private IvParameterSpec iv = new IvParameterSpec(new byte[8]);

    public DES4ADC(String hexString) {
        this.desKey = HexString.toBytes(hexString);
    }

    public DES4ADC(byte[] desKey) {
        this.desKey = desKey;
    }

    public byte[] doDecrypt(byte[] encryptText) throws GeneralSecurityException {
        byte[] rawKeyData = this.desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(2, key, this.iv);
        byte[] decryptedData = cipher.doFinal(encryptText);
        return decryptedData;
    }

    public byte[] doEncrypt(byte[] plainText) throws GeneralSecurityException {
        byte[] rawKeyData = this.desKey;
        DESKeySpec dks = new DESKeySpec(rawKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(1, key, this.iv);
        byte[] encryptedData = cipher.doFinal(plainText);
        return encryptedData;
    }
}