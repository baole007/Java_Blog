package com.jdxl.seedcourse.constant;

import com.cloud.core.result.MsgInfoInterface;


public enum MsgInfoLottery implements MsgInfoInterface {
    SAMPLE("0000", "{0} and {1} are parameters in this Msg"),
    COMMON_PARAMS_IS_LACK("1001", "缺少参数 {0}"),
    COMMON_PARAMS_INVALID("1002", "参数非法"),
    COMMON_PARAMS_FORMAT_ERROR("1003", "{0} 参数格式错误: {1}");

    private String code;
    private String message;

    private MsgInfoLottery(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return "[" + this.code + "]" + this.message;
    }
}
