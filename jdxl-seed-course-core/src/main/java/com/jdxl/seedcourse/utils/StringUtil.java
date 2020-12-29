package com.jdxl.seedcourse.utils;


import java.util.Arrays;

public class StringUtil {
    /**
     * 首字母大写
     *
     * @param name
     * @return
     */
    public static String captureName(String name) {
        // name = name.substring(0, 1).toUpperCase() + name.substring(1);
        // return  name;
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static String getLastLetter(String str, int num) {
        if (str.length() < num) {
            return str;
        } else {
            int strLastIndex = str.length() - 1;
            return str.substring(strLastIndex - num , strLastIndex);
        }
    }

    public static boolean checkIncludeArray(String[] arrayPart, String[] arrayAll) {
        if (arrayPart.length > arrayAll.length) {
            return false;
        }
        String arrayAllStr = Arrays.toString(arrayAll);
        for (int i = 0; i < arrayPart.length; i++) {
            if (arrayAllStr.indexOf(arrayPart[i]) == -1) {
                return false;
            }
        }
        return true;
    }
}