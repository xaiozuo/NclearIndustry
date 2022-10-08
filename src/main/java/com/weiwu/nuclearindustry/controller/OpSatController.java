package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.repositories.OpticalSatelliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping(value = "/optical_satellite")
@CrossOrigin(origins="*",maxAge=3600)
public class OpSatController {

    @Autowired
    private OpticalSatelliteRepository opticalSatelliteRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<OpticalSatellite> query(){
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<OpticalSatellite> save(@RequestBody OpticalSatellite entity){
        opticalSatelliteRepository.save(entity);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public List<OpticalSatellite> delete(@PathVariable Long id){
        opticalSatelliteRepository.deleteById(id);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public List<OpticalSatellite> update(@RequestBody OpticalSatellite entity){
        opticalSatelliteRepository.save(entity);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }
}
