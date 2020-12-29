package com.jdxl.seedcourse.constant;


public interface RedisConstantInterface {
    // 采用接口(Interface)的中变量默认为static final的特性。

    // REDIS 的分割符
    String REDIS_NAME_SPLIT = ":";
    //    REDIS 前缀
    String REDIS_PREFIX = "lottery" + REDIS_NAME_SPLIT + "mts" + REDIS_NAME_SPLIT;
}
