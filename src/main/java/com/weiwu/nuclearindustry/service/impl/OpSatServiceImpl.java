package com.weiwu.nuclearindustry.service.impl;

import com.weiwu.nuclearindustry.entity.OpticalSatellite;
import com.weiwu.nuclearindustry.repositories.OpticalSatelliteRepository;
import com.weiwu.nuclearindustry.service.OpSatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpSatServiceImpl implements OpSatService {
    @Autowired
    private OpticalSatelliteRepository opticalSatelliteRepository;

    @Override
    public void create(OpticalSatellite opticalSatellite) {
        opticalSatelliteRepository.save(opticalSatellite);
    }

    @Override
    public void delete() {

    }

    @Override
    public void update(OpticalSatellite opticalSatellite) {
        opticalSatelliteRepository.save(opticalSatellite);
    }

    @Override
    public void search() {

    }
}
