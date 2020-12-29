package com.jdxl.seedcourse.api;

import com.jdxl.seedcourse.bean.CityReqBean;
import com.jdxl.seedcourse.bean.CityRespBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CityInf {

    @PostMapping("/city")
    CityRespBean queryCityByName(@RequestBody CityReqBean cityReqBean);

}
