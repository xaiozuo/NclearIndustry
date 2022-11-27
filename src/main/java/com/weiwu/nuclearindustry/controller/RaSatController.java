package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.config.SystemConfig;
import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.repositories.RadarSatelliteRepository;
import com.weiwu.nuclearindustry.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@ResponseBody
@RequestMapping(value = "/radar_satellite")
@CrossOrigin(origins="*",maxAge=3600)
public class RaSatController {

    @Autowired
    private RadarSatelliteRepository radarSatelliteRepository;

    @Autowired
    private SystemConfig systemConfig;

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
        Optional<RadarSatellite> byId = radarSatelliteRepository.findById(id);
        if(byId.isPresent()){
            RadarSatellite ras = byId.get();
            deleteByOne(ras);
        }
        radarSatelliteRepository.deleteById(id);
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
    public List<RadarSatellite> deleteBatch(@RequestBody List<Long> ids) {
        Iterable<RadarSatellite> allById = radarSatelliteRepository.findAllById(ids);
        deleteByBatch(allById);
        radarSatelliteRepository.deleteAllById(ids);
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }

    private void deleteByBatch(Iterable<RadarSatellite> raSas){
        for (RadarSatellite raSa : raSas){
            deleteByOne(raSa);
        }
    }
    private void deleteByBatch(List<RadarSatellite> raSas){
        for (RadarSatellite raSa : raSas) {
            deleteByOne(raSa);
        }
    }

    private void deleteByOne(RadarSatellite raSa){
        FileUtil.deleteFile(raSa.getOriginPath());
    }

    @RequestMapping(value = "/removeDuplicate", method = RequestMethod.POST)
    public List<RadarSatellite> removeDuplicate() {
        List<RadarSatellite> raSas = (List<RadarSatellite>) radarSatelliteRepository.findAll();
        ArrayList<RadarSatellite> duplicates = new ArrayList<>();
        TreeSet<RadarSatellite> radarSatellites = new TreeSet<>(Comparator.comparing(RadarSatellite::toString));
        for (RadarSatellite raSa : raSas){
            if(radarSatellites.contains(raSa)){
                duplicates.add(raSa);
            } else {
                radarSatellites.add(raSa);
            }
        }
        deleteByBatch(duplicates);
        List<Long> ids = duplicates.stream().map(RadarSatellite::getId).collect(Collectors.toList());
        radarSatelliteRepository.deleteAllById(ids);
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }

    @RequestMapping(value = "/lightQuery", method = RequestMethod.GET)
    public List<RadarSatellite> lightQuery() {
        List<RadarSatellite> raSas = (List<RadarSatellite>) radarSatelliteRepository.findAll();
        return raSas.stream().collect(Collectors.
                collectingAndThen(Collectors.toCollection(
                                () -> new TreeSet<>(Comparator.comparing(RadarSatellite::getDirectory))),
                        ArrayList::new));
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public List<RadarSatellite> update(@RequestBody RadarSatellite entity){
        radarSatelliteRepository.save(entity);
        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }
}
