package com.jdxl.seedcourse.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wuzhengxue on 2018/12/5.
 */
@Data
@NoArgsConstructor
public class CityRespBean {
    /**
     * 城市编号
     */
    private Long id;

    /**
     * 省份编号
     */
    private Long provinceId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 描述
     */
    private String description;

}
