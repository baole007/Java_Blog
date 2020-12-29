package com.jdxl.seedcourse.utils;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    private static final String IV_STR = "qazwert12345!@#$";
    private static final String PADDING = "AES/CBC/PKCS5Padding";

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        if (StringUtils.isBlank(data)) {
            throw new Exception("encrypt data can't null or empty");
        }
        if (StringUtils.isEmpty(key)) {
            throw new Exception("encrypt key can't null or empty");
        }

        SecretKeySpec skeySpec = getKey(key);
        Cipher cipher = Cipher.getInstance(PADDING);
        IvParameterSpec iv = new IvParameterSpec(IV_STR.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));

        String result = org.springframework.util.Base64Utils.encodeToString(encrypted);
        return result;
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws Exception {


        if (StringUtils.isBlank(data)) {
            throw new Exception("decrypt data can't null or empty");
        }
        if (StringUtils.isEmpty(key)) {
            throw new Exception("decrypt key can't null or empty");
        }

        SecretKeySpec skeySpec = getKey(key);
        Cipher cipher = Cipher.getInstance(PADDING);
        IvParameterSpec iv = new IvParameterSpec(IV_STR.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] encrypted = org.springframework.util.Base64Utils.decodeFromString(data);
        byte[] original = cipher.doFinal(encrypted);
        String originalString = new String(original, "utf-8");

        return originalString;
    }

    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes();
        byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");

        return skeySpec;
    }

    public static void main(String[] args) {
        String str = "test";
        String key = "fKJ8jwsj1nHNkKom";

        try {
            String encry = encrypt(str, key);
            System.out.println(encry);
            String originStr = decrypt(encry, key);
            System.out.println(originStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
