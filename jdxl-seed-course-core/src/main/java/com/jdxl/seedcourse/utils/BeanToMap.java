package com.jdxl.seedcourse.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class BeanToMap {

    /**
     * 将 object 转成 map 类型
     * @param obj
     * @param includeNull
     * @return
     */
    public static Map objToMap(Object obj, boolean includeNull) {
        ObjectMapper oMapper = new ObjectMapper();
        if (!includeNull) {
            oMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        }
        return oMapper.convertValue(obj, Map.class);
    }
}
