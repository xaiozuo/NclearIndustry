package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.repositories.RadarSatelliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping(value = "/radar_satellite")
@CrossOrigin(origins="*",maxAge=3600)
public class RaSatController {

    @Autowired
    private RadarSatelliteRepository radarSatelliteRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<RadarSatellite> query(){
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<RadarSatellite> save(@RequestBody RadarSatellite entity){
        radarSatelliteRepository.save(entity);
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public List<RadarSatellite> delete(@PathVariable Long id){
        radarSatelliteRepository.deleteById(id);
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public List<RadarSatellite> update(@RequestBody RadarSatellite entity){
        radarSatelliteRepository.save(entity);
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }
}
