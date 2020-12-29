package com.jdxl.seedcourse.dao;

import com.jdxl.seedcourse.entity.City;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 城市 DAO 接口类
 * Created by moccanism on 17/11/14.
 */
@Repository
public interface CityDao {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName 城市名
     */
    City findByName(@Param("cityName") String cityName);
}
