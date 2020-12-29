package com.jdxl.seedcourse.service;


import com.jdxl.seedcourse.entity.City;

/**
 * 城市业务逻辑接口类
 *
 * Created by moccanism on 17/11/14.
 */
public interface CityService {

    /**
     * 根据城市名称，查询城市信息
     * @param cityName 城市名称
     */
    City findByName(String cityName);

}