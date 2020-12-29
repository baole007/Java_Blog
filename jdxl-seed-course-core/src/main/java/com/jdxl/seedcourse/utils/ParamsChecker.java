package com.jdxl.seedcourse.utils;


import com.cloud.core.constant.MsgInfoEnum;
import com.cloud.core.exception.BizException;

import java.util.regex.Pattern;

/**
 * 参数校验器
 */
public class ParamsChecker {


    /**
     * 参数校验器，主要是使用正则表达式进行验证。
     */
    public static boolean paramChecker(String regexStr, String varName, String regexContent){
        String trimRegexContent = regexContent.trim();
        if (!Pattern.matches(regexStr, trimRegexContent))
            throw new BizException(MsgInfoEnum.PARAMS_FORMAT_ERROR, varName);
        return true;
    }


}
