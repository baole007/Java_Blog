package com.jdxl.seedcourse.utils;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by liubo on 2018/12/19.
 */
@Slf4j
public class EncryptUtil {
    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param strText
     * @return
     */
    public static String SHA256(final String strText)
    {
        return SHA(strText, "SHA-256");
    }

    public static String powerBetSHA512(TreeMap<String,String> map, String key)
    {
        try {
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<String,String> entry:map.entrySet()){
                sb.append(entry.getValue());
            }
            sb.append(key);
            return SHA512(sb.toString().toLowerCase());
        }catch (Exception e){
            log.error("powerBetSHA512 err:",e.getStackTrace());
            return null;
        }
    }

    private static String getStr(TreeMap<String, String> map){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String> entry:map.entrySet()){
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    public static String powerBetHmacSHA256(TreeMap<String,String> map, String key)
    {
        try {
            String str = getStr(map);
            log.info("待加密串:{}", str);
            return HMACSHA256(str.toLowerCase().getBytes(),key.getBytes());
        }catch (Exception e){
            log.error("powerBetSHA512 err:",e.getStackTrace());
            return null;
        }
    }

    public static String powerBetHmacSHA256Obj(TreeMap<String,Object> map, String key)
    {
        try {
            String str = getStrObj(map);
            log.info("待加密串:{}", str);
            return HMACSHA256(str.toLowerCase().getBytes(),key.getBytes());
        }catch (Exception e){
            log.error("powerBetSHA512 err:",e.getStackTrace());
            return null;
        }
    }
    private static String getStrObj(TreeMap<String, Object> map){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,Object> entry:map.entrySet()){
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    public static String HMACSHA256(byte[] data, byte[] key)
    {
        try  {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2hex(byte[] b)
    {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param strText
     * @return
     */
    public static String SHA512(final String strText)
    {
        return SHA(strText, "SHA-512");
    }

    private static String SHA(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    public static void main(String[] args) {
        TreeMap<String,String> map = new TreeMap();
        map.put("ReqID","ke28302430279408025733");
//        map.put("UserName","0793672258");
//        map.put("Country","ke");
//        map.put("Amount","1");
//        map.put("ForUserName","0793672258");
        map.put("TransactionID","bf22fd698a3744cc9e7bcedeffa87d3e");
//        String s = "100ke78574989012345678785749890aff7134a94c88ceea48e";
//        System.out.print(EncryptUtil.powerBetSHA512(map,""));

        String str = "h5ng17759@qq.comot01136712918881234";
        String key = "TESTACC245DE849HGI23BFCCDSLLSM";
        System.out.print(HMACSHA256(str.toLowerCase().getBytes(),key.getBytes()));
    }

    public static String hmacSHA3_512(String key, String str) {
        Digest digest = new SHA3Digest(512);

        org.bouncycastle.crypto.Mac mac = new HMac(digest);
        mac.init(new KeyParameter(key.getBytes()));

        mac.update(str.getBytes(), 0, str.getBytes().length);
        byte[] macV = new byte[mac.getMacSize()];
        int res = mac.doFinal(macV, 0);
        return byte2hex(macV);
    }
}
