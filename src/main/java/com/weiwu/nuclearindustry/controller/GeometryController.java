package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.repositories.WorldBoundaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
@RequestMapping(value = "/world_boundary")
public class GeometryController {

    @Autowired
    private WorldBoundaryRepository worldBoundaryRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/{nation}")
    public String queryByNation(@PathVariable String nation){
        return worldBoundaryRepository.findByChinaName(nation);
    }
}
