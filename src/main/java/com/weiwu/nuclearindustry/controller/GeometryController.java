package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.repositories.GeometryChinaRepository;
import com.weiwu.nuclearindustry.repositories.GeometryWorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping(value = "/geometry")
public class GeometryController {

    @Autowired
    private GeometryWorldRepository geometryWorldRepository;
    @Autowired
    private GeometryChinaRepository geometryChinaRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/world/{nation}")
    public String queryByNation(@PathVariable String nation){
        return geometryWorldRepository.findByNationName(nation);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/china/{province}/{city}/{county}")
    public String queryByRegion(@PathVariable String province,  @PathVariable String city,
                                @PathVariable String county){
        return geometryChinaRepository.findByCode(Integer.valueOf(province), Integer.valueOf(city),
                Integer.valueOf(county));
    }
}
