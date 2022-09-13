package com.weiwu.nuclearindustry.service.impl;

import com.weiwu.nuclearindustry.entity.RadarSatellite;
import com.weiwu.nuclearindustry.repositories.RadarSatelliteRepository;
import com.weiwu.nuclearindustry.service.RaSatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaSatServiceImpl implements RaSatService {
    @Autowired
    private RadarSatelliteRepository radarSatelliteRepository;

    @Override
    public void create(RadarSatellite radarSatellite) {
        radarSatelliteRepository.save(radarSatellite);
    }

    @Override
    public void delete() {

    }

    @Override
    public void update(RadarSatellite radarSatellite) {
        radarSatelliteRepository.save(radarSatellite);
    }

    @Override
    public void search() {

    }
}
