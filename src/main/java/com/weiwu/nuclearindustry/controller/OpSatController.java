package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.config.SystemConfig;
import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.User;
import com.weiwu.nuclearindustry.repositories.OpticalSatelliteRepository;
import com.weiwu.nuclearindustry.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    Specification<OpticalSatellite> getSpecification(String type){
        return new Specification<OpticalSatellite> (){
            @Override
            public Predicate toPredicate(Root<OpticalSatellite> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("satelliteID")) , "%" + type.toLowerCase() + "%");
            }
        };
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{type}/{page}")
    public Page<OpticalSatellite> queryByPage(@PathVariable String type, @PathVariable int page) {
        int pageSize = 10;
        String sortBy = "satelliteID", sortOrder = "ASC";
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Specification<OpticalSatellite> spec = getSpecification(type);
        Page<OpticalSatellite> sciencePropagandaPage = opticalSatelliteRepository.findAll(spec, pageable);
        return sciencePropagandaPage;
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
