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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    Specification<OpticalSatellite> getSpecification(String type, String search){
        return new Specification<OpticalSatellite> (){
            @Override
            public Predicate toPredicate(Root<OpticalSatellite> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                String lowerCaseType = "%" + type.toLowerCase() + "%";
                String lowerCaseSearch = "%" + search.toLowerCase() + "%";
                Predicate predicate0 = criteriaBuilder.like(criteriaBuilder.lower(root.get("satelliteID")) , lowerCaseType);
                Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.lower(root.get("produceType")) , lowerCaseSearch);
                Predicate predicate2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("satelliteID")) , lowerCaseSearch);
                Predicate predicate3 = criteriaBuilder.like(criteriaBuilder.lower(root.get("sceneID")) , lowerCaseSearch);
                Predicate predicate4 = criteriaBuilder.like(criteriaBuilder.lower(root.get("sensorID")) , lowerCaseSearch);
                Predicate searchPredicate = criteriaBuilder.or(predicate1, predicate2, predicate3, predicate4);
                return criteriaBuilder.and(predicate0, searchPredicate);
            }
        };
    }

    public Pageable getPageable(int page) {
        int pageSize = 10;
        String sortBy = "satelliteID", sortOrder = "ASC";
        Sort sort = sortOrder.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(page, pageSize, sort);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{type}/{page}")
    public Page<OpticalSatellite> queryByPage(@PathVariable String type, @PathVariable int page) {
        Pageable pageable = getPageable(page);
        Specification<OpticalSatellite> spec = getSpecification(type);
        Page<OpticalSatellite> sciencePropagandaPage = opticalSatelliteRepository.findAll(spec, pageable);
        return sciencePropagandaPage;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{type}/{search}/{page}")
    public Page<OpticalSatellite> searchByPage(@PathVariable String type, @PathVariable String search, @PathVariable int page) {
        Pageable pageable = getPageable(page);
        Specification<OpticalSatellite> spec = getSpecification(type, search);
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
        ArrayList<OpticalSatellite> toDelete = new ArrayList<>();  // 用于存储要删除的数据
        ArrayList<OpticalSatellite> toDeleteForFileNotExist = new ArrayList<>();  // 新增：用于存储因文件不存在要删除的数据

        String targetPath = "\\\\HEXI-WS-2\\zy3立体原始数据";
        TreeSet<OpticalSatellite> opticalSatellites = new TreeSet<>(Comparator.comparing(OpticalSatellite::toString));
        TreeSet<OpticalSatellite> opticalSatellites1 = new TreeSet<>(Comparator.comparing(OpticalSatellite::toStringForDuplicate));
        for (OpticalSatellite opSa : opSas){
            String filePath = opSa.getOriginPath();
//            String fileName = filePath.substring(filePath.lastIndexOf('\\') + 1);
//            File file = new File(targetPath + "\\" + fileName);
            File file = new File(filePath);
            if (!file.exists()) {
                try {
                    new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    toDeleteForFileNotExist.add(opSa);
                }
//            } else {
//                if (opticalSatellites.contains(opSa)) {
//                    duplicates.add(opSa);
//                    if (!opSa.getOriginPath().equals(targetPath+"\\"+fileName)) {  // 如果重复且不是目标路径，标记为删除
//                        toDelete.add(opSa);
//                    }
//                } else {
//                    opticalSatellites.add(opSa);
//                    if (!opSa.getOriginPath().equals(targetPath)) {  // 如果不是目标路径且后续有相同数据，标记为删除
//                        toDelete.add(opSa);
//                    }
//                }
//            }
            }
        }
        deleteByBatch(toDeleteForFileNotExist);  // 新增：删除文件不存在的数据
        List<Long> ids2 = toDeleteForFileNotExist.stream().map(OpticalSatellite::getId).collect(Collectors.toList());

        opticalSatelliteRepository.deleteAllById(ids2);
        List<OpticalSatellite> opSas1 = (List<OpticalSatellite>) opticalSatelliteRepository.findAll();

        for (OpticalSatellite opSa : opSas1){
            if(opticalSatellites.contains(opSa)){
                duplicates.add(opSa);
            } else {
                opticalSatellites.add(opSa);
            }
        }
        deleteByBatch(duplicates);
        List<Long> ids1 = duplicates.stream().map(OpticalSatellite::getId).collect(Collectors.toList());
        opticalSatelliteRepository.deleteAllById(ids1);
        List<OpticalSatellite> opSas2 = (List<OpticalSatellite>) opticalSatelliteRepository.findAll();

        for (OpticalSatellite opSa : opSas2){
            if(opticalSatellites1.contains(opSa)){
                duplicates.add(opSa);
                if (!opSa.getOriginPath().equals(targetPath)) {  // 如果重复且不是目标路径，标记为删除
                    toDelete.add(opSa);
                }
            } else {
                opticalSatellites1.add(opSa);
            }
        }
        deleteByBatch(duplicates);
        List<Long> ids4 = duplicates.stream().map(OpticalSatellite::getId).collect(Collectors.toList());
        opticalSatelliteRepository.deleteAllById(ids4);
        List<Long> ids3 = toDelete.stream().map(OpticalSatellite::getId).collect(Collectors.toList());
        opticalSatelliteRepository.deleteAllById(ids3);

        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
//            if(opticalSatellites.contains(opSa)){
//                duplicates.add(opSa);
//                if (!opSa.getOriginPath().equals(targetPath)) {  // 如果重复且不是目标路径，标记为删除
//                    toDelete.add(opSa);
//                }
//            } else {
//                opticalSatellites.add(opSa);
//                String filePath = opSa.getOriginPath();  // 假设您有一个字段存储文件地址
//                File file = new File(filePath);
//                if (!file.exists()) {
//                    toDeleteForFileNotExist.add(opSa);  // 新增：如果文件不存在，添加到待删除列表
//                }
//                if (!opSa.getOriginPath().equals(targetPath)) {  // 如果不是目标路径且后续有相同数据，标记为删除
//                    toDelete.add(opSa);
//                }
//            }
//        }
//        deleteByBatch(duplicates);
//        List<Long> ids1 = duplicates.stream().map(OpticalSatellite::getId).collect(Collectors.toList());
//
//        deleteByBatch(toDeleteForFileNotExist);  // 新增：删除文件不存在的数据
//        List<Long> ids2 = toDeleteForFileNotExist.stream().map(OpticalSatellite::getId).collect(Collectors.toList());
//        List<Long> ids3 = toDelete.stream().map(OpticalSatellite::getId).collect(Collectors.toList());
//
//        opticalSatelliteRepository.deleteAllById(ids1);
//        opticalSatelliteRepository.deleteAllById(ids2);
//        opticalSatelliteRepository.deleteAllById(ids3);
//
//        return (List<OpticalSatellite>) opticalSatelliteRepository.findAll();
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
