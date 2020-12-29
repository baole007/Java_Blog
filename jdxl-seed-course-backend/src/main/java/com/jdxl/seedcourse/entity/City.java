package com.jdxl.seedcourse.entity;

import lombok.Data;

/**
 * Created by moccanism on 17/11/14.
 */
@Data
public class City {

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
