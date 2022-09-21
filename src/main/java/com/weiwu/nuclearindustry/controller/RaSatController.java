package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.repositories.RadarSatelliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping(value = "/radar_satellite")
public class RaSatController {

    @Autowired
    private RadarSatelliteRepository radarSatelliteRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<RadarSatellite> query(){
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<RadarSatellite> save(RadarSatellite entity){
        radarSatelliteRepository.save(entity);
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public List<RadarSatellite> delete(Long id){
        radarSatelliteRepository.deleteById(id);
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public List<RadarSatellite> update(RadarSatellite entity){
        radarSatelliteRepository.save(entity);
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }
}
