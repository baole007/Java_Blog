package com.jdxl.seedcourse.utils;

import com.google.common.io.BaseEncoding;

public class Base64Utils {
    /**
     * <p>
     * BASE64字符串解码为二进制数据
     * </p>
     *
     * @param base64 BASE64字符串
     * @return 解码字符串
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        return BaseEncoding.base64().decode(base64);
    }

    /**
     * <p>
     * 二进制数据编码为BASE64字符串
     * </p>
     *
     * @param bytes 字符串的字节数组
     * @return BASE64编码字符串
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        return BaseEncoding.base64().encode(bytes);
    }
}
