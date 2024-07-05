package com.weiwu.nuclearindustry.controller;

import com.weiwu.nuclearindustry.config.SystemConfig;
import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.repositories.RadarSatelliteRepository;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    Specification<RadarSatellite> getSpecification(String type){
        return new Specification<RadarSatellite> (){
            @Override
            public Predicate toPredicate(Root<RadarSatellite> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get("satellite")) , "%" + type.toLowerCase() + "%");
            }
        };
    }

    Specification<RadarSatellite> getSpecification(String type, String search){
        return new Specification<RadarSatellite> (){
            @Override
            public Predicate toPredicate(Root<RadarSatellite> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                String lowerCaseType = "%" + type.toLowerCase() + "%";
                String lowerCaseSearch = "%" + search.toLowerCase() + "%";
                Predicate predicate0 = criteriaBuilder.like(criteriaBuilder.lower(root.get("satellite")) , lowerCaseType);
                Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("imagingMode")) , lowerCaseSearch);
                Predicate predicate2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("satellite")) , lowerCaseSearch);
                Predicate predicate3 = criteriaBuilder.like(criteriaBuilder.lower(root.get("sceneID")) , lowerCaseSearch);
                Predicate predicate4 = criteriaBuilder.like(criteriaBuilder.lower(root.get("sensorID")) , lowerCaseSearch);
                Predicate searchPredicate = criteriaBuilder.or(predicate1, predicate2, predicate3, predicate4);
                return criteriaBuilder.and(predicate0, searchPredicate);
            }
        };
    }

    public Pageable getPageable(int page) {
        int pageSize = 10;
        String sortBy = "satellite", sortOrder = "ASC";
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(page, pageSize, sort);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{type}/{page}")
    public Page<RadarSatellite> queryByPage(@PathVariable String type, @PathVariable int page) {
        Pageable pageable = getPageable(page);
        Specification<RadarSatellite> spec = getSpecification(type);
        Page<RadarSatellite> sciencePropagandaPage = radarSatelliteRepository.findAll(spec, pageable);
        return sciencePropagandaPage;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{type}/{search}/{page}")
    public Page<RadarSatellite> searchByPage(@PathVariable String type, @PathVariable String search, @PathVariable int page) {
        Pageable pageable = getPageable(page);
        Specification<RadarSatellite> spec = getSpecification(type, search);
        Page<RadarSatellite> sciencePropagandaPage = radarSatelliteRepository.findAll(spec, pageable);
        return sciencePropagandaPage;
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
        ArrayList<RadarSatellite> toDelete = new ArrayList<>();  // 用于存储要删除的数据
        ArrayList<RadarSatellite> toDeleteForFileNotExist = new ArrayList<>();  // 新增：用于存储因文件不存在要删除的数据

        String targetPath = "\\\\HEXI-WS-2\\zy3立体原始数据";
        TreeSet<RadarSatellite> radarSatellites = new TreeSet<>(Comparator.comparing(RadarSatellite::toString));
        TreeSet<RadarSatellite> radarSatellites1 = new TreeSet<>(Comparator.comparing(RadarSatellite::toString1));
        for (RadarSatellite raSa : raSas){
            String filePath = raSa.getOriginPath();
            File file = new File(filePath);
            if (!file.exists()) {
                try {
                    new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    toDeleteForFileNotExist.add(raSa);
                }
            }
        }
        deleteByBatch(toDeleteForFileNotExist);  // 新增：删除文件不存在的数据
        List<Long> ids2 = toDeleteForFileNotExist.stream().map(RadarSatellite::getId).collect(Collectors.toList());
        radarSatelliteRepository.deleteAllById(ids2);
        List<RadarSatellite> raSas1 = (List<RadarSatellite>) radarSatelliteRepository.findAll();
        for (RadarSatellite raSa : raSas1){
            if(radarSatellites.contains(raSa)){
                duplicates.add(raSa);
            } else {
                radarSatellites.add(raSa);
            }
        }
        deleteByBatch(duplicates);
        List<Long> ids1 = duplicates.stream().map(RadarSatellite::getId).collect(Collectors.toList());
        radarSatelliteRepository.deleteAllById(ids1);
        List<RadarSatellite> raSa2 = (List<RadarSatellite>) radarSatelliteRepository.findAll();

        for (RadarSatellite raSa : raSa2){
            if(radarSatellites1.contains(raSa)){
                duplicates.add(raSa);
                if (!raSa.getOriginPath().equals(targetPath)) {  // 如果重复且不是目标路径，标记为删除
                    toDelete.add(raSa);
                }
            } else {
                radarSatellites1.add(raSa);
            }
        }
        deleteByBatch(duplicates);
        List<Long> ids4 = duplicates.stream().map(RadarSatellite::getId).collect(Collectors.toList());
        radarSatelliteRepository.deleteAllById(ids4);
        List<Long> ids3 = toDeleteForFileNotExist.stream().map(RadarSatellite::getId).collect(Collectors.toList());
        radarSatelliteRepository.deleteAllById(ids3);


        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
    }
//    public List<RadarSatellite> removeDuplicate() {
//        List<RadarSatellite> raSas = (List<RadarSatellite>) radarSatelliteRepository.findAll();
//        ArrayList<RadarSatellite> duplicates = new ArrayList<>();
//        ArrayList<RadarSatellite> toDeleteForFileNotExist = new ArrayList<>();  // 新增：用于存储因文件不存在要删除的数据
//
//        TreeSet<RadarSatellite> radarSatellites = new TreeSet<>(Comparator.comparing(RadarSatellite::toString));
//        for (RadarSatellite raSa : raSas){
//            if(radarSatellites.contains(raSa)){
//                duplicates.add(raSa);
//            } else {
//                radarSatellites.add(raSa);
//
//                String filePath = raSa.getOriginPath();  // 假设您有一个字段存储文件地址
//                File file = new File(filePath);
//                if (!file.exists()) {
//                    toDeleteForFileNotExist.add(raSa);  // 新增：如果文件不存在，添加到待删除列表
//                }
//            }
//        }
//        deleteByBatch(duplicates);
//        List<Long> ids1 = duplicates.stream().map(RadarSatellite::getId).collect(Collectors.toList());
//
//        deleteByBatch(toDeleteForFileNotExist);  // 新增：删除文件不存在的数据
//        List<Long> ids2 = toDeleteForFileNotExist.stream().map(RadarSatellite::getId).collect(Collectors.toList());
//
//        radarSatelliteRepository.deleteAllById(ids1);
//        radarSatelliteRepository.deleteAllById(ids2);
//
//        return (List<RadarSatellite>) radarSatelliteRepository.findAll();
//    }

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
