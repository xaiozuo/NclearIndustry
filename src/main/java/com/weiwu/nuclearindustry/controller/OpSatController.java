package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.repositories.OpticalSatelliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping(value = "/optical_satellite")
public class OpSatController {

    @Autowired
    private OpticalSatelliteRepository opticalSatelliteRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<OpticalSatellite> query(){
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<OpticalSatellite> save(OpticalSatellite entity){
        opticalSatelliteRepository.save(entity);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public List<OpticalSatellite> delete(Long id){
        opticalSatelliteRepository.deleteById(id);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public List<OpticalSatellite> update(OpticalSatellite entity){
        opticalSatelliteRepository.save(entity);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }
}
