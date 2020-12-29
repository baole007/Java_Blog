package com.jdxl.seedcourse.constant;


public interface RabbitmqConstantInterface {
    // 采用接口(Interface)的中变量默认为static final的特性。

    // Rabbitmq分割符
    String SPLIT = ":";
    // Rabbitmq前缀
    String TEST_PRE = "";  // 如果不是测试请将该串设置成空字符串
    String QUEUE_NAME_PRE = TEST_PRE + ConstantInterface.PROJECT_NAME + SPLIT;

    String ORDER_QUEUE = QUEUE_NAME_PRE + "order";
    String ORDER_EXCHANGE = QUEUE_NAME_PRE + "exchange" + SPLIT + "order";

}
