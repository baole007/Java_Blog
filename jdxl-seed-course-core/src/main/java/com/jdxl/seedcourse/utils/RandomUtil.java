package com.jdxl.seedcourse.utils;

import java.util.Random;

public class RandomUtil {

    /**
     * 生成随机数
     *
     * @param start 开始的点
     * @param end   结束的点
     * @return int类型的随机数
     */
    public static int buildRandomNum(int start, int end) {
        double random = Math.random();
        return ((int) random * (end - start)) + start;
    }

    /**
     * 生成随机数
     * 目前能生成的随机数的长度最长是16
     *
     * @param randomLong 随机数的长度
     * @return 随机数的字符串
     */
    public static String buildRandomNum(int randomLong) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < randomLong) {
            stringBuilder.append(String.valueOf(Math.abs(random.nextLong())));
        }
        return stringBuilder.substring(0, randomLong);
    }
}
