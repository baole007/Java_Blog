package com.jdxl.seedcourse.service.impl;

import com.jdxl.seedcourse.dao.CityDao;
import com.jdxl.seedcourse.entity.City;
import com.jdxl.seedcourse.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    public City findByName(String cityName) {
        return cityDao.findByName(cityName);
    }

}
