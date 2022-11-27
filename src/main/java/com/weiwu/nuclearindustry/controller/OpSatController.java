package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.config.SystemConfig;
import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.repositories.OpticalSatelliteRepository;
import com.weiwu.nuclearindustry.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
@ResponseBody
@RequestMapping(value = "/optical_satellite")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OpSatController {

    @Autowired
    private OpticalSatelliteRepository opticalSatelliteRepository;

    @Autowired
    private SystemConfig systemConfig;

    @RequestMapping(method = RequestMethod.GET)
    public List<OpticalSatellite> query() {
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<OpticalSatellite> save(@RequestBody OpticalSatellite entity) {
        opticalSatelliteRepository.save(entity);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public List<OpticalSatellite> delete(@PathVariable Long id) {
        Optional<OpticalSatellite> byId = opticalSatelliteRepository.findById(id);
        if (byId.isPresent()) {
            OpticalSatellite ops = byId.get();
            deleteByOne(ops);
        }
        opticalSatelliteRepository.deleteById(id);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    public List<OpticalSatellite> deleteBatch(@RequestBody List<Long> ids) {
        Iterable<OpticalSatellite> allById = opticalSatelliteRepository.findAllById(ids);
        deleteByBatch(allById);
        opticalSatelliteRepository.deleteAllById(ids);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    private void deleteByBatch(Iterable<OpticalSatellite> opSas){
        for (OpticalSatellite opSa : opSas){
            deleteByOne(opSa);
        }
    }
    private void deleteByBatch(List<OpticalSatellite> opSas){
        for (OpticalSatellite opSa : opSas) {
            deleteByOne(opSa);
        }
    }

    private void deleteByOne(OpticalSatellite opSa){
        FileUtil.deleteFile(opSa.getOriginPath());
    }

    @RequestMapping(value = "/removeDuplicate", method = RequestMethod.POST)
    public List<OpticalSatellite> removeDuplicate() {
        List<OpticalSatellite> opSas = (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
        ArrayList<OpticalSatellite> duplicates = new ArrayList<>();
        TreeSet<OpticalSatellite> opticalSatellites = new TreeSet<>(Comparator.comparing(OpticalSatellite::toString));
        for (OpticalSatellite opSa : opSas){
            if(opticalSatellites.contains(opSa)){
                duplicates.add(opSa);
            } else {
                opticalSatellites.add(opSa);
            }
        }
        deleteByBatch(duplicates);
        List<Long> ids = duplicates.stream().map(OpticalSatellite::getId).collect(Collectors.toList());
        opticalSatelliteRepository.deleteAllById(ids);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }

    @RequestMapping(value = "/lightQuery", method = RequestMethod.GET)
    public List<OpticalSatellite> lightQuery() {
        List<OpticalSatellite> opSas = (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
        return opSas.stream().collect(Collectors.
                collectingAndThen(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(OpticalSatellite::getDirectory))),
                ArrayList::new));
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public List<OpticalSatellite> update(@RequestBody OpticalSatellite entity) {
        opticalSatelliteRepository.save(entity);
        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
    }
}
