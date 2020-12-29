package com.jdxl.seedcourse.controller;

import com.cloud.base.controller.BaseController;
import com.cloud.utils.mapper.BeanMapper;
import com.jdxl.seedcourse.api.CityInf;
import com.jdxl.seedcourse.bean.CityReqBean;
import com.jdxl.seedcourse.bean.CityRespBean;
import com.jdxl.seedcourse.entity.City;
import com.jdxl.seedcourse.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController extends BaseController implements CityInf {

    @Autowired
    CityService cityService;

    @Override
    public CityRespBean queryCityByName(@RequestBody CityReqBean cityReqBean) {

        City city = cityService.findByName(cityReqBean.getCityName());

        // 接口不能直接向前端返回entity，每个接口一对出入参，ReqBean and RespBean
        CityRespBean result = new CityRespBean();
        BeanMapper.copy(city, result);

        return result;
    }

}
