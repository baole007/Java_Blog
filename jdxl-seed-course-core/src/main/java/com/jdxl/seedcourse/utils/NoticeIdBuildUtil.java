package com.jdxl.seedcourse.utils;

import com.cloud.shardingsphere.keygen.IPSectionKeyGenerator;

public class NoticeIdBuildUtil {
    public static Number buildUniqueNumber() {
        IPSectionKeyGenerator ip = new IPSectionKeyGenerator();
        return ip.generateKey();
    }

    public static String buildNoticeId(String mchId) {
        StringBuilder sb = new StringBuilder();
        sb.append(mchId).append(buildUniqueNumber().longValue());
        return sb.toString();
    }

}
