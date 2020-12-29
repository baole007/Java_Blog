package com.jdxl.seedcourse.utils;

import com.cloud.shardingsphere.keygen.IPSectionKeyGenerator;

public class BuilderUtil {
    private static IPSectionKeyGenerator iPSectionKeyGenerator =new IPSectionKeyGenerator();

    /**
     *
     * @param mchId
     * @param handleId
     * @param userId
     * @return String
     */
    public static String buildFlowId(String mchId, String handleId,String userId) {
        return buildCommonId(mchId, userId);
    }

    public static String buildUserId(String mchId ,String mchUserId) {
        if(iPSectionKeyGenerator == null){
            iPSectionKeyGenerator =new IPSectionKeyGenerator();
        }
        return iPSectionKeyGenerator.generateKey().toString();
    }

    public static String buildUserAccountId(String mchId, String mchAccountId, String userId) {
        return buildCommonId(mchId, userId);
    }

    public static String buildRefunFlowId(String mchId, String handleId,String refundId) {
        return buildCommonId(mchId, refundId);
    }

    public static String buildCommonId(String mchId, String id){
        if(iPSectionKeyGenerator == null){
            iPSectionKeyGenerator =new IPSectionKeyGenerator();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mchId);
        stringBuilder.append(iPSectionKeyGenerator.generateKey());
        stringBuilder.append(id.substring(id.length()-2));
        return stringBuilder.toString();
    }

    /**
     * 构建三方支付时需要的类名
     *
     * @param thirdName
     * @return
     */
    public static String buildThirdPayClassName(String thirdName) {
        return thirdName + "ThirdPayImpl";
    }

    //
    public static String buildHandleId(int lengthHandleId) {
        return RandomUtil.buildRandomNum(lengthHandleId);
    }
}
